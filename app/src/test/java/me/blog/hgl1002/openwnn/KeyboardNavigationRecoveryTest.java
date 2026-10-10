package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import me.blog.hgl1002.openwnn.view.KeyboardNavigationInsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;
import org.robolectric.util.ReflectionHelpers;

/** Exercises real ancestor layout changes without synthesizing another inset callback. */
@RunWith(RobolectricTestRunner.class)
@Config(sdk = {33, 35})
@LooperMode(LooperMode.Mode.PAUSED)
public class KeyboardNavigationRecoveryTest {
    private ActivityController<Activity> activityController;
    private KeyboardNavigationInsets navigation;
    private View decor;
    private FrameLayout container;
    private LinearLayout input;
    private View keys;

    @Before
    public void setUp() {
        activityController = Robolectric.buildActivity(Activity.class);
        Activity activity = activityController.get();
        activity.setTheme(android.R.style.Theme_Material_Light_NoActionBar);
        activityController.setup();
        Window window = activity.getWindow();
        window.setDecorFitsSystemWindows(false);
        decor = window.getDecorView();
        container = new FrameLayout(activity);
        input = new LinearLayout(activity);
        input.setOrientation(LinearLayout.VERTICAL);
        keys = new View(activity);
        input.addView(
                keys, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        container.addView(
                input,
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        Gravity.BOTTOM));
        activity.setContentView(container);
        navigation = new KeyboardNavigationInsets(window, Color.BLACK);
        navigation.update(input, Color.BLACK);
        assertTrue(input.isAttachedToWindow());
        setRootInsets(48);
        decor.dispatchApplyWindowInsets(decor.getRootWindowInsets());
        layout();
    }

    @After
    public void tearDown() {
        if (navigation != null) navigation.close();
        activityController.pause().stop().destroy();
    }

    @Test
    public void lostPaddingIsRepairedAfterLayoutWithoutInsetDispatch() {
        input.setPadding(0, 0, 0, 0);
        layout();
        assertEquals(48, input.getPaddingBottom());
        assertKeysAboveNavigation();
    }

    @Test
    public void parentSpacingIsCountedOnceAndRemovingItRestoresPadding() {
        container.setPadding(0, 0, 0, 48);
        layout();
        assertEquals(0, input.getPaddingBottom());
        assertKeysAboveNavigation();
        container.setPadding(0, 0, 0, 0);
        layout();
        assertEquals(48, input.getPaddingBottom());
        assertKeysAboveNavigation();
    }

    @Test
    public void partialParentSpacingOnlyNeedsTheRemainingInset() {
        container.setPadding(0, 0, 0, 20);
        layout();
        assertEquals(28, input.getPaddingBottom());
        assertKeysAboveNavigation();
        layout();
        assertEquals(28, input.getPaddingBottom());
    }

    @Test
    public void reopeningUsesFreshRootInsetsBeforeAnotherDispatch() {
        setRootInsets(24);
        navigation.update(input, Color.BLACK);
        assertEquals(24, input.getPaddingBottom());
        layout();
        assertEquals(24, input.getPaddingBottom());
    }

    @Test
    public void reattachingInputRestoresSpacingBeforeAnotherInsetCallback() {
        for (int repeat = 0; repeat < 3; repeat++) {
            ViewGroup.LayoutParams parameters = input.getLayoutParams();
            container.removeView(input);
            input.setPadding(0, 0, 0, 0);
            container.addView(input, parameters);
            assertTrue(input.isAttachedToWindow());
            layout();
            assertEquals(48, input.getPaddingBottom());
            assertKeysAboveNavigation();
        }
    }

    @Test
    public void closingControllerStopsLayoutRepairs() {
        navigation.close();
        input.setPadding(0, 0, 0, 0);
        layout();
        assertEquals(0, input.getPaddingBottom());
    }

    private void setRootInsets(int bottom) {
        WindowInsets insets =
                new WindowInsets.Builder()
                        .setInsets(
                                WindowInsets.Type.navigationBars(),
                                android.graphics.Insets.of(0, 0, 0, bottom))
                        .setInsetsIgnoringVisibility(
                                WindowInsets.Type.navigationBars(),
                                android.graphics.Insets.of(0, 0, 0, bottom))
                        .setVisible(WindowInsets.Type.navigationBars(), bottom > 0)
                        .build();
        // Simulate the ViewRoot's latest system data independently from callback delivery.
        ReflectionHelpers.setField(decor.getParent(), "mLastWindowInsets", insets);
        assertEquals(
                bottom,
                decor.getRootWindowInsets().getInsets(WindowInsets.Type.navigationBars()).bottom);
    }

    private void layout() {
        // Allow the first layout's geometry correction to be measured in the following pass.
        for (int pass = 0; pass < 2; pass++) {
            decor.measure(
                    View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(1200, View.MeasureSpec.EXACTLY));
            decor.layout(0, 0, 800, 1200);
            decor.getViewTreeObserver().dispatchOnGlobalLayout();
        }
    }

    private void assertKeysAboveNavigation() {
        int[] location = new int[2];
        keys.getLocationInWindow(location);
        assertEquals(decor.getHeight() - 48, location[1] + keys.getHeight());
    }
}
