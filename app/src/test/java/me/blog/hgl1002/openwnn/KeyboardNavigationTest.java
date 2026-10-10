package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.inputmethodservice.InputMethodService;
import android.os.Build;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;

import androidx.core.graphics.Insets;
import androidx.core.view.WindowInsetsCompat;
import androidx.preference.PreferenceManager;

import me.blog.hgl1002.openwnn.event.InputViewChangeEvent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.android.controller.ServiceController;
import org.robolectric.annotation.Config;
import org.robolectric.util.ReflectionHelpers;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {21, 28, 33, 35})
public class KeyboardNavigationTest {
    private ServiceController<SebeolHangulIME> controller;
    private SebeolHangulIME ime;
    private ViewGroup input;
    private Window window;
    private View decor;

    @Before
    public void setUp() {
        PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.getApplication())
                .edit()
                .clear()
                .putBoolean("initial_launch", false)
                .apply();
        Resources resources = RuntimeEnvironment.getApplication().getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.keyboard = Configuration.KEYBOARD_NOKEYS;
        configuration.hardKeyboardHidden = Configuration.HARDKEYBOARDHIDDEN_YES;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        controller = Robolectric.buildService(SebeolHangulIME.class).create();
        ime = controller.get();
        EditorInfo editor = new EditorInfo();
        editor.inputType = InputType.TYPE_CLASS_TEXT;
        ((InputMethodService.InputMethodImpl) ime.onCreateInputMethodInterface())
                .startInput(new BaseInputConnection(new View(ime), true), editor);
        input = (ViewGroup) ime.onCreateInputView();
        window = ime.getWindow().getWindow();
        decor = window.getDecorView();
        // Exercise the case where Android lays out the input window behind the system bar.
        setLayoutBehindNavigation(true);
    }

    @After
    public void tearDown() {
        controller.destroy();
    }

    @Test
    public void keysSitAboveThreeButtonNavigation() {
        int keyboardHeight = measureInput();
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        assertEquals(48, input.getPaddingBottom());
        assertEquals(keyboardHeight + 48, measureInput());
        assertEquals(input.getHeight() - 48, input.getChildAt(0).getBottom());
    }

    @Test
    public void spacingTracksGestureModeAndDisappearingNavigation() {
        int keyboardHeight = measureInput();
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        decor.dispatchApplyWindowInsets(navigation(0, 0, 20));
        assertEquals(keyboardHeight + 20, measureInput());
        decor.dispatchApplyWindowInsets(navigation(0, 0, 0));
        assertEquals(0, input.getPaddingBottom());
        assertEquals(keyboardHeight, measureInput());
    }

    @Test
    public void repeatedInsetsAndReopeningDoNotAccumulateSpacing() {
        int keyboardHeight = measureInput();
        for (int repeat = 0; repeat < 3; repeat++) {
            ime.onWindowShown();
            decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
            decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
            assertEquals(keyboardHeight + 48, measureInput());
        }
    }

    @Test
    public void reopeningRecoversPaddingWithoutAnotherInsetCallback() {
        int keyboardHeight = measureInput();
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        for (int repeat = 0; repeat < 3; repeat++) {
            ime.hideWindow();
            input.setPadding(0, 0, 0, 0);
            ime.onWindowShown();
            assertEquals(48, input.getPaddingBottom());
            assertEquals(keyboardHeight + 48, measureInput());
        }
    }

    @Test
    public void replacingInputViewReusesInsetsWithoutAnotherCallback() {
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        View oldInput = input;
        ime.onInputViewChange(new InputViewChangeEvent());
        input = (ViewGroup) ime.onCreateInputView();
        assertNotSame(oldInput, input);
        assertEquals(48, input.getPaddingBottom());
        decor.dispatchApplyWindowInsets(navigation(0, 0, 20));
        assertEquals(20, input.getPaddingBottom());
        assertEquals(48, oldInput.getPaddingBottom());
    }

    @Test
    @Config(sdk = {33, 35})
    public void visibleBarWithEmptyCurrentInsetsKeepsStableSpace() {
        WindowInsets temporarilyEmpty =
                new WindowInsets.Builder(navigation(0, 0, 48))
                        .setInsets(WindowInsets.Type.navigationBars(), android.graphics.Insets.NONE)
                        .setVisible(WindowInsets.Type.navigationBars(), true)
                        .build();
        decor.dispatchApplyWindowInsets(temporarilyEmpty);
        assertEquals(48, input.getPaddingBottom());
        ime.onWindowShown();
        assertEquals(48, input.getPaddingBottom());
    }

    @Test
    @Config(sdk = {33, 35})
    public void actuallyHiddenBarDoesNotKeepStableSpace() {
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        WindowInsets hidden =
                new WindowInsets.Builder(navigation(0, 0, 48))
                        .setInsets(WindowInsets.Type.navigationBars(), android.graphics.Insets.NONE)
                        .setVisible(WindowInsets.Type.navigationBars(), false)
                        .build();
        decor.dispatchApplyWindowInsets(hidden);
        ime.onWindowShown();
        assertEquals(0, input.getPaddingBottom());
    }

    @Test
    @Config(sdk = {33, 35})
    public void visibleCaptionWithEmptyInsetsUsesItsOwnStableSpace() {
        WindowInsets insets =
                new WindowInsets.Builder(navigation(0, 0, 24))
                        .setInsets(WindowInsets.Type.captionBar(), android.graphics.Insets.NONE)
                        .setInsetsIgnoringVisibility(
                                WindowInsets.Type.captionBar(),
                                android.graphics.Insets.of(0, 0, 0, 48))
                        .setVisible(WindowInsets.Type.captionBar(), true)
                        .build();
        decor.dispatchApplyWindowInsets(insets);
        assertEquals(48, input.getPaddingBottom());
    }

    @Test
    public void landscapeNavigationMovesSpacingToItsCurrentEdge() {
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        decor.dispatchApplyWindowInsets(navigation(0, 32, 0));
        assertEquals(0, input.getPaddingBottom());
        assertEquals(32, input.getPaddingRight());
        decor.dispatchApplyWindowInsets(navigation(32, 0, 0));
        assertEquals(32, input.getPaddingLeft());
        assertEquals(0, input.getPaddingRight());
    }

    @Test
    public void alreadyHandledInsetsDoNotLeaveExtraSpacing() {
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        // An ancestor can reserve the system bar area and pass zero remaining insets.
        decor.dispatchApplyWindowInsets(navigation(0, 0, 0));
        assertEquals(0, input.getPaddingBottom());
    }

    @Test
    @Config(sdk = {28, 33, 35})
    public void spacingIsReservedOnlyOnceWhenWindowFittingChanges() {
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        setLayoutBehindNavigation(false);
        decor.dispatchApplyWindowInsets(navigation(0, 0, 48));
        View content = input;
        while (content.getParent() instanceof View && content.getParent() != decor) {
            content = (View) content.getParent();
        }
        assertSame(decor, content.getParent());
        ViewGroup.MarginLayoutParams margins =
                (ViewGroup.MarginLayoutParams) content.getLayoutParams();
        assertEquals(48, margins.bottomMargin + input.getPaddingBottom());
        // Android 15 enforces drawing behind the bar even if fitting is requested.
        if (Build.VERSION.SDK_INT < 35) {
            assertEquals(48, margins.bottomMargin);
            assertEquals(0, input.getPaddingBottom());
        }
    }

    @Test
    @Config(sdk = {33, 35})
    public void imeCaptionBarUsesTheLargerInsetWithoutDoubleCountingNavigation() {
        WindowInsets insets =
                new WindowInsets.Builder(navigation(0, 0, 24))
                        .setInsets(
                                WindowInsets.Type.captionBar(),
                                android.graphics.Insets.of(0, 0, 0, 48))
                        .build();
        decor.dispatchApplyWindowInsets(insets);
        assertEquals(48, input.getPaddingBottom());
    }

    private int measureInput() {
        input.measure(
                View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        input.layout(0, 0, input.getMeasuredWidth(), input.getMeasuredHeight());
        return input.getMeasuredHeight();
    }

    private void setLayoutBehindNavigation(boolean behind) {
        int flags = decor.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        flags =
                behind
                        ? flags | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        : flags & ~View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        decor.setSystemUiVisibility(flags);
        // No real window token is attached in this test; mirror the ViewRoot flag propagation.
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.systemUiVisibility = flags;
        window.setAttributes(attributes);
        if (Build.VERSION.SDK_INT >= 30) window.setDecorFitsSystemWindows(!behind);
    }

    private static WindowInsets navigation(int left, int right, int bottom) {
        if (Build.VERSION.SDK_INT >= 30) {
            return new WindowInsets.Builder()
                    .setInsets(
                            WindowInsets.Type.navigationBars(),
                            android.graphics.Insets.of(left, 0, right, bottom))
                    .setInsetsIgnoringVisibility(
                            WindowInsets.Type.navigationBars(),
                            android.graphics.Insets.of(left, 0, right, bottom))
                    .setVisible(
                            WindowInsets.Type.navigationBars(), bottom > 0 || left > 0 || right > 0)
                    .build();
        }
        WindowInsets legacy =
                new WindowInsetsCompat.Builder()
                        .setSystemWindowInsets(Insets.of(left, 0, right, bottom))
                        .setStableInsets(Insets.of(left, 0, right, bottom))
                        .build()
                        .toWindowInsets();
        // Before API 29 the public builder cannot populate stable insets in the platform object.
        ReflectionHelpers.setField(legacy, "mStableInsets", new Rect(left, 0, right, bottom));
        ReflectionHelpers.setField(legacy, "mStableInsetsConsumed", false);
        ReflectionHelpers.setField(legacy, "mSystemWindowInsetsConsumed", false);
        return legacy;
    }
}
