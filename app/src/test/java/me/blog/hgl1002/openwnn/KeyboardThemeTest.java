package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.widget.TextView;

import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceManager;

import me.blog.hgl1002.openwnn.view.KeyboardTheme;

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
import org.robolectric.annotation.GraphicsMode;
import org.robolectric.annotation.LooperMode;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {21, 28, 33, 35})
@LooperMode(LooperMode.Mode.PAUSED)
public class KeyboardThemeTest {
    private SharedPreferences preferences;
    private ServiceController<SebeolHangulIME> controller;
    private SebeolHangulIME ime;

    @Before
    public void setUp() {
        preferences =
                PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.getApplication());
        preferences.edit().clear().putBoolean("initial_launch", false).apply();
        updateConfiguration(false, false);
        controller = Robolectric.buildService(SebeolHangulIME.class).create();
        ime = controller.get();
    }

    @After
    public void tearDown() {
        controller.destroy();
    }

    @Test
    public void systemOptionIsFirstAndPreferenceDefaultsToIt() {
        assertEquals("system", ime.getResources().getStringArray(R.array.keyboard_skin_id)[0]);
        assertEquals(
                ime.getString(R.string.preference_keyboard_system),
                ime.getResources().getStringArray(R.array.keyboard_skin)[0]);
        PreferenceManager manager = new PreferenceManager(ime);
        ListPreference skin =
                manager.inflateFromResource(ime, R.xml.openwnn_pref_ko_appearance, null)
                        .findPreference("keyboard_skin");
        assertNotNull(skin);
        assertEquals("system", skin.getValue());
    }

    @Test
    public void defaultKeyboardAndNavigationFollowSystemChanges() {
        assertSurfaces(KeyboardTheme.WHITE);
        ime.onConfigurationChanged(updateConfiguration(true, false));
        assertSurfaces(KeyboardTheme.DARK);
        ime.onConfigurationChanged(updateConfiguration(false, false));
        assertSurfaces(KeyboardTheme.WHITE);
    }

    @Test
    public void explicitSkinsOverrideSystemAndRefreshWithoutRestart() {
        for (KeyboardTheme theme : KeyboardTheme.values()) {
            preferences.edit().putString("keyboard_skin", theme.getSkinId()).apply();
            Shadows.shadowOf(Looper.getMainLooper()).idle();
            assertSurfaces(theme);
            ime.onConfigurationChanged(updateConfiguration(true, false));
            assertSurfaces(theme);
            ime.onConfigurationChanged(updateConfiguration(false, false));
            assertSurfaces(theme);
        }
        preferences.edit().putString("keyboard_skin", "system").apply();
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        ime.onConfigurationChanged(updateConfiguration(true, false));
        assertSurfaces(KeyboardTheme.DARK);
    }

    @Test
    public void hardwareIndicatorAndWelcomePanelUseSelectedTheme() {
        preferences
                .edit()
                .putString("keyboard_skin", "dark")
                .putBoolean("initial_launch", true)
                .apply();
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        ime.onConfigurationChanged(updateConfiguration(false, true));
        View input = ime.onCreateInputView();
        View indicators = input.findViewById(R.id.subview);
        assertNotNull(indicators);
        assertEquals(KeyboardTheme.DARK.getBackgroundColor(ime), backgroundColor(indicators));
        TextView language = input.findViewById(R.id.lang);
        assertEquals(Color.WHITE, language.getCurrentTextColor());
        ime.updateMetaKeyStateDisplay();
        assertEquals(
                KeyboardTheme.DARK.getBackgroundColor(ime),
                backgroundColor(input.findViewById(R.id.shift)));
        assertEquals(
                KeyboardTheme.DARK.getBackgroundColor(ime),
                backgroundColor(input.findViewById(R.id.alt)));
        TextView help = input.findViewById(R.id.initial_launch_help);
        assertEquals(Color.WHITE, help.getCurrentTextColor());
        assertEquals(
                KeyboardTheme.DARK.getBackgroundColor(ime),
                backgroundColor((View) help.getParent()));
    }

    @Test
    @Config(sdk = {28, 33, 35})
    @GraphicsMode(GraphicsMode.Mode.NATIVE)
    public void navigationInsetIsPaintedWithoutCoveringEditor() {
        preferences.edit().putString("keyboard_skin", "dark").apply();
        ime.onCreateInputView();
        View decor = ime.getWindow().getWindow().getDecorView();
        WindowInsets insets = navigationInsets(0, 20);
        decor.dispatchApplyWindowInsets(insets);
        decor.dispatchApplyWindowInsets(insets);
        assertEquals(Color.TRANSPARENT, drawSurface(decor.getBackground()).getPixel(50, 50));
        assertEquals(
                KeyboardTheme.DARK.getBackgroundColor(ime),
                drawSurface(decor.getBackground()).getPixel(50, 95));

        // Landscape navigation can move to the side, and stale bottom insets must disappear.
        decor.dispatchApplyWindowInsets(navigationInsets(12, 0));
        Bitmap landscape = drawSurface(decor.getBackground());
        assertEquals(KeyboardTheme.DARK.getBackgroundColor(ime), landscape.getPixel(95, 50));
        assertEquals(Color.TRANSPARENT, landscape.getPixel(50, 95));
    }

    private void assertSurfaces(KeyboardTheme theme) {
        View input = ime.onCreateInputView();
        assertEquals(theme, KeyboardTheme.resolve(ime));
        assertEquals(theme.getBackgroundColor(ime), backgroundColor(input));
        ime.onWindowShown();
        Window window = ime.getWindow().getWindow();
        int expectedNavigation =
                Build.VERSION.SDK_INT < 26 && theme == KeyboardTheme.WHITE
                        ? Color.BLACK
                        : theme.getBackgroundColor(ime);
        if (Build.VERSION.SDK_INT < 35)
            assertEquals(expectedNavigation, window.getNavigationBarColor());
        if (Build.VERSION.SDK_INT >= 26) {
            assertEquals(
                    theme == KeyboardTheme.WHITE,
                    new WindowInsetsControllerCompat(window, window.getDecorView())
                            .isAppearanceLightNavigationBars());
        }
        if (Build.VERSION.SDK_INT >= 29) assertFalse(window.isNavigationBarContrastEnforced());
    }

    private static int backgroundColor(View view) {
        return ((ColorDrawable) view.getBackground()).getColor();
    }

    private static Bitmap drawSurface(Drawable surface) {
        Bitmap bitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        surface.setBounds(0, 0, 100, 100);
        surface.draw(new Canvas(bitmap));
        return bitmap;
    }

    private static WindowInsets navigationInsets(int right, int bottom) {
        if (Build.VERSION.SDK_INT >= 30) {
            return new WindowInsets.Builder()
                    .setInsets(
                            WindowInsets.Type.navigationBars(),
                            android.graphics.Insets.of(0, 0, right, bottom))
                    .build();
        }
        return new WindowInsetsCompat.Builder()
                .setSystemWindowInsets(androidx.core.graphics.Insets.of(0, 24, right, bottom))
                .build()
                .toWindowInsets();
    }

    private static Configuration updateConfiguration(boolean night, boolean hardware) {
        Resources resources = RuntimeEnvironment.getApplication().getResources();
        Configuration config = new Configuration(resources.getConfiguration());
        config.uiMode =
                (config.uiMode & ~Configuration.UI_MODE_NIGHT_MASK)
                        | (night
                                ? Configuration.UI_MODE_NIGHT_YES
                                : Configuration.UI_MODE_NIGHT_NO);
        config.keyboard = hardware ? Configuration.KEYBOARD_QWERTY : Configuration.KEYBOARD_NOKEYS;
        config.hardKeyboardHidden =
                hardware
                        ? Configuration.HARDKEYBOARDHIDDEN_NO
                        : Configuration.HARDKEYBOARDHIDDEN_YES;
        resources.updateConfiguration(config, resources.getDisplayMetrics());
        return config;
    }
}
