package me.blog.hgl1002.openwnn.view;

import static org.junit.Assert.*;

import android.app.Activity;
import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;
import android.widget.TextView;

import androidx.core.view.WindowInsetsCompat;

import me.blog.hgl1002.openwnn.BuildConfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 33)
@LooperMode(LooperMode.Mode.PAUSED)
public class KeyboardLayoutDiagnosticsTest {
    @Test
    public void historyDeduplicatesUnchangedStateAcrossCallbackTypes() {
        KeyboardLayoutDiagnostics.History history = new KeyboardLayoutDiagnostics.History(3);
        history.add("layout", "state-1", "extra");
        history.add("draw", "state-1", "extra");
        assertEquals(1, history.size());
        assertTrue(history.dump().contains("layout"));
    }

    @Test
    public void historyKeepsOnlyTheMostRecentStatesAndRetainsChangesBackToAnOldState() {
        KeyboardLayoutDiagnostics.History history = new KeyboardLayoutDiagnostics.History(3);
        history.add("draw", "discarded-state", "");
        history.add("draw", "closed-fold", "");
        history.add("draw", "open-fold", "");
        history.add("draw", "closed-fold", "");
        assertEquals(3, history.size());
        assertFalse(history.dump().contains("discarded-state"));
        assertEquals(2, history.dump().split("closed-fold", -1).length - 1);
    }

    @Test
    public void snapshotPreservesIncomingAndRemainingInsetsAndExcludesEditorText() {
        ActivityController<Activity> controller = Robolectric.buildActivity(Activity.class).setup();
        try {
            Activity activity = controller.get();
            TextView input = new TextView(activity);
            input.setText("PRIVATE_EDITOR_CONTENT_8668");
            input.setContentDescription("PRIVATE_ACCESSIBILITY_TEXT_8668");
            activity.setContentView(input);
            input.setPadding(0, 0, 0, 12);
            View decor = activity.getWindow().getDecorView();
            decor.measure(View.MeasureSpec.makeMeasureSpec(800, View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(1200, View.MeasureSpec.EXACTLY));
            decor.layout(0, 0, 800, 1200);
            WindowInsets incoming = new WindowInsets.Builder()
                    .setInsets(WindowInsets.Type.navigationBars(), Insets.NONE)
                    .setInsetsIgnoringVisibility(WindowInsets.Type.navigationBars(), Insets.of(0, 0, 0, 48))
                    .setVisible(WindowInsets.Type.navigationBars(), false)
                    .build();
            WindowInsets remaining = new WindowInsets.Builder()
                    .setInsets(WindowInsets.Type.navigationBars(), Insets.of(0, 0, 0, 20))
                    .build();
            KeyboardLayoutDiagnostics.record("test", activity.getWindow(), input, incoming, remaining, true);
            String report = KeyboardLayoutDiagnostics.report();
            assertTrue(report.contains("version=" + BuildConfig.VERSION_NAME));
            assertTrue(report.contains("incoming=nav=[0,0,0,0] stable=[0,0,0,48] visible=false"));
            assertTrue(report.contains("remaining=nav=[0,0,0,20]"));
            assertTrue(report.contains("padding=[0,0,0,12]"));
            assertTrue(report.contains("paddingNeedsLayout=true"));
            assertFalse(report.contains("PRIVATE_EDITOR_CONTENT_8668"));
            assertFalse(report.contains("PRIVATE_ACCESSIBILITY_TEXT_8668"));
        } finally {
            controller.pause().stop().destroy();
        }
    }

    @Test
    @Config(sdk = 21)
    public void legacyInsetsAreIncludedWithoutModernApiCalls() {
        ActivityController<Activity> controller = Robolectric.buildActivity(Activity.class).setup();
        try {
            WindowInsets insets = new WindowInsetsCompat.Builder()
                    .setSystemWindowInsets(androidx.core.graphics.Insets.of(0, 24, 0, 48))
                    .build().toWindowInsets();
            KeyboardLayoutDiagnostics.record("legacy-test", controller.get().getWindow(), null, insets, insets, false);
            assertTrue(KeyboardLayoutDiagnostics.report().contains("incoming=legacy=[0,24,0,48]"));
        } finally {
            controller.pause().stop().destroy();
        }
    }
}
