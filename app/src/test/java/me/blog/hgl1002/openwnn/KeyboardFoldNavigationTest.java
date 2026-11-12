package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.res.Configuration;
import android.graphics.Insets;
import android.graphics.Rect;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.SystemClock;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowMetrics;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputBinding;

import androidx.preference.PreferenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.Implementation;
import org.robolectric.annotation.Implements;
import org.robolectric.annotation.LooperMode;
import org.robolectric.shadows.ShadowDisplay;
import org.robolectric.shadows.ShadowWindowManagerImpl;
import org.robolectric.util.ReflectionHelpers;

/** The IME's root insets can lag the window manager during a fold/display transition. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {33, 35}, shadows = KeyboardFoldNavigationTest.MetricsWindowManager.class)
@LooperMode(LooperMode.Mode.PAUSED)
public class KeyboardFoldNavigationTest {
    private ServiceController<SebeolHangulIME> controller;
    private SebeolHangulIME ime;
    private View decor;
    private ViewGroup input;
    private BaseInputConnection connection;
    private int width = 800;
    private int height = 1200;

    @Before
    public void setUp() {
        MetricsWindowManager.metrics = metrics(800, 1200, 48);
        PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.getApplication())
                .edit().clear().putBoolean("initial_launch", false)
                .putBoolean("popup_preview", false).apply();
        controller = Robolectric.buildService(SebeolHangulIME.class).create();
        ime = controller.get();
        InputMethodService.InputMethodImpl method =
                (InputMethodService.InputMethodImpl) ime.onCreateInputMethodInterface();
        // Robolectric has no system-server DisplayArea to attach a real IME token to.
        ReflectionHelpers.callInstanceMethod(ime.getWindow(), "setToken",
                ReflectionHelpers.ClassParameter.from(IBinder.class, new Binder()));
        if (Build.VERSION.SDK_INT == 33) {
            // Android 13 initializes this in the system's initializeInternal IPC; newer
            // versions do it in onCreate. Supply the same unhandled-config policy here.
            ReflectionHelpers.callInstanceMethod(
                    ReflectionHelpers.getField(ime, "mConfigTracker"), "onInitialize",
                    ReflectionHelpers.ClassParameter.from(int.class, 0));
        }
        EditorInfo editor = new EditorInfo();
        editor.inputType = InputType.TYPE_CLASS_TEXT;
        connection = new BaseInputConnection(new View(ime), true);
        method.bindInput(new InputBinding(connection, new Binder(), 0, 0));
        method.startInput(connection, editor);
        Window window = ime.getWindow().getWindow();
        window.setDecorFitsSystemWindows(false);
        ime.showWindow(true);
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        decor = window.getDecorView();
        input = (ViewGroup) ime.onCreateInputView();
        assertTrue(input.isAttachedToWindow());
        dispatchRootInsets(navigation(48));
        layout();
    }

    @After
    public void tearDown() {
        controller.destroy();
        MetricsWindowManager.metrics = null;
    }

    @Test
    public void openingAfterFoldReservesNavigationEvenWhenRootInsetsAreEmpty() {
        for (int repeat = 0; repeat < 2; repeat++) {
            ime.hideWindow();
            foldTo(1200, 1600, 64);
            ime.showWindow(true);
            layout();
            assertKeysAboveBottomBar(64);

            ime.hideWindow();
            foldTo(800, 1200, 48);
            ime.showWindow(true);
            layout();
            assertKeysAboveBottomBar(48);
        }
    }

    @Test
    public void foldingWhileKeyboardIsVisibleAlsoReservesNavigation() {
        foldTo(1200, 1600, 64);
        layout();
        assertKeysAboveBottomBar(64);
        foldTo(800, 1200, 48);
        layout();
        assertKeysAboveBottomBar(48);
    }

    @Test
    public void freshRootInsetsDoNotDoubleTheMetricsFallback() {
        foldTo(1200, 1600, 64);
        layout();
        assertKeysAboveBottomBar(64);
        dispatchRootInsets(navigation(64));
        layout();
        assertKeysAboveBottomBar(64);
    }

    @Test
    public void hiddenNavigationDoesNotReserveStableMetricsSpace() {
        foldTo(1200, 1600, 64);
        WindowInsets hidden = new WindowInsets.Builder(navigation(64))
                .setInsets(WindowInsets.Type.navigationBars(), Insets.NONE)
                .setVisible(WindowInsets.Type.navigationBars(), false)
                .build();
        MetricsWindowManager.metrics = new WindowMetrics(new Rect(0, 0, width, height), hidden);
        dispatchRootInsets(hidden);
        layout();
        assertKeysAboveBottomBar(0);
    }

    @Test
    public void windowAlreadyAboveNavigationDoesNotAddAnotherGap() {
        // The window manager already shortened the IME window by the bar height.
        height = 1152;
        dispatchRootInsets(navigation(0));
        layout();
        assertKeysAboveBottomBar(0);
    }

    @Test
    public void missingMetricsDoesNotBreakRootInsetHandling() {
        MetricsWindowManager.metrics = null;
        dispatchRootInsets(navigation(48));
        layout();
        assertKeysAboveBottomBar(48);
    }

    @Test
    public void bottomEdgeOfSpaceKeyRemainsTouchableAfterFolding() {
        ime.hideWindow();
        foldTo(1200, 1600, 64);
        ime.showWindow(true);
        layout();
        KeyboardView keyboard = input.findViewById(R.id.keyboard);
        Keyboard.Key space = keyboard.getKeyboard().getKeys().stream()
                .filter(key -> key.codes[0] == -10).findFirst().get();
        int[] position = new int[2];
        keyboard.getLocationOnScreen(position);
        assertTrue("The bottom of the space key must be outside system navigation",
                position[1] + space.y + space.height <= height - 64);
        keyboard.getLocationInWindow(position);
        long time = SystemClock.uptimeMillis();
        for (int action : new int[] {MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP}) {
            MotionEvent event = MotionEvent.obtain(time, time, action,
                    position[0] + space.x + space.width / 2f,
                    position[1] + space.y + space.height - 1, 0);
            decor.dispatchTouchEvent(event);
            event.recycle();
        }
        assertEquals(" ", connection.getEditable().toString());
    }

    @Test
    public void parentProvidedSpaceIsNotAddedAgainByMetricsFallback() {
        foldTo(1200, 1600, 64);
        View parent = (View) input.getParent();
        parent.setPadding(0, 0, 0, 64);
        layout();
        assertEquals(0, input.getPaddingBottom());
        assertKeysAboveBottomBar(64);
        parent.setPadding(0, 0, 0, 0);
        layout();
        assertKeysAboveBottomBar(64);
    }

    @Test
    public void metricsFallbackTracksGestureAndSideNavigation() {
        foldTo(1200, 1600, 24);
        layout();
        assertKeysAboveBottomBar(24);
        for (boolean left : new boolean[] {false, true}) {
            Insets bar = Insets.of(left ? 32 : 0, 0, left ? 0 : 32, 0);
            WindowInsets side = new WindowInsets.Builder()
                    .setInsets(WindowInsets.Type.navigationBars(), bar)
                    .setInsetsIgnoringVisibility(WindowInsets.Type.navigationBars(), bar)
                    .setVisible(WindowInsets.Type.navigationBars(), true).build();
            MetricsWindowManager.metrics = new WindowMetrics(new Rect(0, 0, width, height), side);
            dispatchRootInsets(navigation(0));
            layout();
            assertKeysAboveBottomBar(0);
            View keys = input.getChildAt(input.getChildCount() - 1);
            int[] position = new int[2];
            keys.getLocationOnScreen(position);
            assertEquals(left ? 32 : 0, position[0]);
            // A keyboard may be narrower than the available area; it must fit inside it.
            assertTrue(position[0] + keys.getWidth() <= width - (left ? 0 : 32));
        }
    }

    @Test
    public void unavailableMetricsWithEmptyRootCanRecoverOnNextLayout() {
        foldTo(1200, 1600, 64);
        MetricsWindowManager.metrics = null;
        layout();
        assertKeysAboveBottomBar(0);
        MetricsWindowManager.metrics = metrics(width, height, 64);
        layout();
        assertKeysAboveBottomBar(64);
    }

    private void foldTo(int newWidth, int newHeight, int navigationHeight) {
        View oldInput = input;
        width = newWidth;
        height = newHeight;
        ShadowDisplay displayState = Shadows.shadowOf(
                ime.getWindow().getWindow().getWindowManager().getDefaultDisplay());
        displayState.setWidth(width);
        displayState.setHeight(height);
        MetricsWindowManager.metrics = metrics(width, height, navigationHeight);
        dispatchRootInsets(navigation(0));
        Configuration config = new Configuration(ime.getResources().getConfiguration());
        float density = ime.getResources().getDisplayMetrics().density;
        config.screenWidthDp = (int) (width / density);
        config.screenHeightDp = (int) (height / density);
        // Folding need not change orientation, but it does change the screen dimensions.
        config.orientation = Configuration.ORIENTATION_PORTRAIT;
        DisplayMetrics display = new DisplayMetrics();
        display.setTo(ime.getResources().getDisplayMetrics());
        display.widthPixels = width;
        display.heightPixels = height;
        ime.getResources().updateConfiguration(config, display);
        ime.onConfigurationChanged(config);
        input = (ViewGroup) ime.onCreateInputView();
        assertNotSame(oldInput, input);
        assertTrue("Configuration reset must attach the new keyboard", input.isAttachedToWindow());
    }

    private void dispatchRootInsets(WindowInsets insets) {
        ReflectionHelpers.setField(decor.getParent(), "mLastWindowInsets", insets);
        decor.dispatchApplyWindowInsets(insets);
    }

    private void layout() {
        for (int pass = 0; pass < 3; pass++) {
            decor.measure(View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.AT_MOST));
            decor.layout(0, 0, width, decor.getMeasuredHeight());
            // Position the wrap-content IME window at the screen bottom as WindowManager does.
            Object attachInfo = ReflectionHelpers.getField(decor, "mAttachInfo");
            ReflectionHelpers.setField(attachInfo, "mWindowTop", height - decor.getHeight());
            decor.getViewTreeObserver().dispatchOnGlobalLayout();
        }
        assertFalse("Drawing must resume once the keys are safely laid out",
                decor.getViewTreeObserver().dispatchOnPreDraw());
    }

    private void assertKeysAboveBottomBar(int bottom) {
        View keys = input.getChildAt(input.getChildCount() - 1);
        int[] position = new int[2];
        keys.getLocationOnScreen(position);
        assertEquals(height - bottom, position[1] + keys.getHeight());
    }

    private static WindowMetrics metrics(int width, int height, int bottom) {
        return new WindowMetrics(new Rect(0, 0, width, height), navigation(bottom));
    }

    private static WindowInsets navigation(int bottom) {
        return new WindowInsets.Builder()
                .setInsets(WindowInsets.Type.navigationBars(), Insets.of(0, 0, 0, bottom))
                .setInsetsIgnoringVisibility(
                        WindowInsets.Type.navigationBars(), Insets.of(0, 0, 0, bottom))
                .setVisible(WindowInsets.Type.navigationBars(), bottom > 0)
                .build();
    }

    @Implements(className = "android.view.WindowManagerImpl", isInAndroidSdk = false)
    public static class MetricsWindowManager extends ShadowWindowManagerImpl {
        static WindowMetrics metrics;

        @Implementation
        protected WindowMetrics getCurrentWindowMetrics() {
            if (metrics == null) throw new IllegalStateException("Metrics unavailable");
            return metrics;
        }
    }
}
