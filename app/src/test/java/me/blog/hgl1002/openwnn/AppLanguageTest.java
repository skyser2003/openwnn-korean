package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import androidx.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(
        sdk = {21, 28, 33},
        qualifiers = "en-rUS")
public class AppLanguageTest {
    private Context context;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }

    @Test
    public void koreanOverrideLocalizesActivityAndServiceResources() {
        AppLanguage.select(context, "ko");
        assertEquals("ko", AppLanguage.selected(context));
        assertEquals(
                "앱 언어",
                AppLanguage.wrap(context).getString(R.string.preference_app_language_title));
        assertEquals(
                "앱 언어",
                AppLanguage.resources(context, context.getResources())
                        .getString(R.string.preference_app_language_title));
    }

    @Test
    public void systemDefaultRestoresSystemLanguage() {
        AppLanguage.select(context, "ko");
        AppLanguage.select(context, "system");
        assertEquals("system", AppLanguage.selected(context));
        assertEquals(
                "App language",
                AppLanguage.wrap(context).getString(R.string.preference_app_language_title));
    }

    @Test
    public void localizationPreservesNightModeAndHardwareConfiguration() {
        Resources original = context.getResources();
        Configuration configuration = new Configuration(original.getConfiguration());
        configuration.uiMode = Configuration.UI_MODE_NIGHT_YES;
        configuration.keyboard = Configuration.KEYBOARD_QWERTY;
        configuration.hardKeyboardHidden = Configuration.HARDKEYBOARDHIDDEN_NO;
        original.updateConfiguration(configuration, original.getDisplayMetrics());
        AppLanguage.select(context, "ko");
        Resources localized = AppLanguage.resources(context, original);
        assertEquals(
                Configuration.UI_MODE_NIGHT_YES,
                localized.getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK);
        assertEquals(Configuration.KEYBOARD_QWERTY, localized.getConfiguration().keyboard);
        assertEquals(
                Configuration.HARDKEYBOARDHIDDEN_NO,
                localized.getConfiguration().hardKeyboardHidden);
    }
}
