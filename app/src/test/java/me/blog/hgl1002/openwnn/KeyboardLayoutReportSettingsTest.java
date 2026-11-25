package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Insets;
import android.view.View;
import android.view.WindowInsets;

import androidx.preference.Preference;

import me.blog.hgl1002.openwnn.view.KeyboardLayoutDiagnostics;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {21, 28, 35})
public class KeyboardLayoutReportSettingsTest {
    @Test
    @Config(sdk = 35)
    public void settingsContentClearsTappableButtonsWhenNavigationInsetsAreEmpty() {
        ActivityController<SebeolHangulIMEControlPanel> controller =
                Robolectric.buildActivity(SebeolHangulIMEControlPanel.class).setup();
        try {
            View settingsRoot = controller.get().findViewById(R.id.settings_root);
            WindowInsets buttons =
                    new WindowInsets.Builder()
                            .setInsets(WindowInsets.Type.tappableElement(), Insets.of(0, 0, 0, 52))
                            .setVisible(WindowInsets.Type.tappableElement(), true)
                            .build();
            settingsRoot.dispatchApplyWindowInsets(buttons);
            assertEquals(52, settingsRoot.getPaddingBottom());

            WindowInsets hidden =
                    new WindowInsets.Builder()
                            .setInsetsIgnoringVisibility(
                                    WindowInsets.Type.tappableElement(), Insets.of(0, 0, 0, 52))
                            .setVisible(WindowInsets.Type.tappableElement(), false)
                            .build();
            settingsRoot.dispatchApplyWindowInsets(hidden);
            assertEquals(0, settingsRoot.getPaddingBottom());
        } finally {
            controller.pause().stop().destroy();
        }
    }

    @Test
    public void aboutActionCopiesTheLayoutReportOnlyWhenTapped() {
        ActivityController<SebeolHangulIMEControlPanel> controller =
                Robolectric.buildActivity(SebeolHangulIMEControlPanel.class).setup();
        try {
            SebeolHangulIMEControlPanel activity = controller.get();
            activity.getSupportFragmentManager().executePendingTransactions();
            SebeolHangulIMEControlPanel.AboutFragment about = new SebeolHangulIMEControlPanel.AboutFragment();
            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, about).commitNow();
            Preference copy = about.findPreference("copy_keyboard_layout_report");
            assertNotNull(copy);
            ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(
                    android.content.ClipData.newPlainText("existing", "keep this clip"));
            KeyboardLayoutDiagnostics.report();
            assertEquals(
                    "keep this clip", clipboard.getPrimaryClip().getItemAt(0).getText().toString());
            assertTrue(copy.getOnPreferenceClickListener().onPreferenceClick(copy));
            String report = clipboard.getPrimaryClip().getItemAt(0).getText().toString();
            assertTrue(report.startsWith("OpenWnn keyboard layout report"));
            assertTrue(report.contains("version=" + BuildConfig.VERSION_NAME));
        } finally {
            controller.pause().stop().destroy();
        }
    }
}
