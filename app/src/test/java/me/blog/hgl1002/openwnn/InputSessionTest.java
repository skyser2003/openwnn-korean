package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.InputMethodService;
import android.os.Looper;
import android.text.InputType;
import android.view.InputDevice;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;

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
import org.robolectric.annotation.LooperMode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28, 33})
@LooperMode(LooperMode.Mode.PAUSED)
public class InputSessionTest {
    private ServiceController<SebeolHangulIME> controller;
    private SebeolHangulIME ime;
    private SharedPreferences preferences;
    private InputMethodService.InputMethodImpl inputMethod;
    private RecordingConnection connection;

    @Before
    public void setUp() {
        preferences =
                PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.getApplication());
        preferences
                .edit()
                .clear()
                .putBoolean("initial_launch", false)
                .putBoolean("hardware_hide_keyboard", true)
                .putString("hardware_alphabet_layout", "keyboard_alphabet_colemak")
                .putString("hardware_hangul_layout", "keyboard_dubul_standard")
                .putBoolean("hardware_use_moachigi", false)
                .apply();
        configureHardware(true);
        controller = Robolectric.buildService(SebeolHangulIME.class).create();
        ime = controller.get();
        inputMethod = (InputMethodService.InputMethodImpl) ime.onCreateInputMethodInterface();
        // Hidden input sessions do not need an attached window token in Robolectric.
        connection = new RecordingConnection(new View(ime));
        startEditor(connection);
    }

    @After
    public void tearDown() {
        if (controller != null) controller.destroy();
    }

    @Test
    public void hiddenKeyboardInitializesColemakWithoutStartingInputView() {
        assertFalse(ime.onEvaluateInputViewShown());
        assertFalse(ime.onShowInputRequested(InputMethod.SHOW_EXPLICIT, false));
        press(KeyEvent.KEYCODE_G, 0);
        assertEquals("d", connection.getEditable().toString());
        ime.hideWindow();
        press(KeyEvent.KEYCODE_K, 0);
        assertEquals("de", connection.getEditable().toString());
    }

    @Test
    public void hiddenKeyboardStillComposesHangulAfterLanguageKey() {
        press(KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON);
        press(KeyEvent.KEYCODE_R, 0);
        press(KeyEvent.KEYCODE_K, 0);
        assertEquals("가", connection.getEditable().toString());
    }

    @Test
    public void changingEditorsUpdatesConnectionWithoutShowingKeyboard() {
        press(KeyEvent.KEYCODE_G, 0);
        RecordingConnection second = new RecordingConnection(new View(ime));
        startEditor(second);
        press(KeyEvent.KEYCODE_K, 0);
        assertEquals("d", connection.getEditable().toString());
        assertEquals("e", second.getEditable().toString());
    }

    @Test
    public void shortcutFinishesCompositionAndDoesNotInsertText() {
        press(KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON);
        press(KeyEvent.KEYCODE_R, 0);
        press(KeyEvent.KEYCODE_K, 0);
        press(KeyEvent.KEYCODE_G, KeyEvent.META_ALT_ON);
        assertEquals("가", connection.getEditable().toString());
        assertEquals(-1, BaseInputConnection.getComposingSpanStart(connection.getEditable()));
        assertEquals(2, connection.events.size());
        assertEquals(KeyEvent.KEYCODE_D, connection.events.get(0).getKeyCode());
        assertEquals(KeyEvent.ACTION_UP, connection.events.get(1).getAction());
    }

    @Test
    public void ctrlSpaceAndLongPressSettingsTakeEffectWhileServiceIsRunning() {
        preferences
                .edit()
                .putString("system_hardware_lang_key_stroke", "c---62")
                .putString(
                        "system_action_on_lang_key_long_press",
                        SebeolHangulIME.LANGKEY_SWITCH_KOR_ENG)
                .apply();
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        KeyEvent down = key(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE, KeyEvent.META_CTRL_ON);
        assertTrue(ime.onKeyDown(down.getKeyCode(), down));
        Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(600));
        KeyEvent up = key(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SPACE, KeyEvent.META_CTRL_ON);
        assertTrue(ime.onKeyUp(up.getKeyCode(), up));
        press(KeyEvent.KEYCODE_R, 0);
        press(KeyEvent.KEYCODE_K, 0);
        assertEquals("가", connection.getEditable().toString());
    }

    @Test
    public void disconnectingKeyboardRestoresSoftwareKeyboardEligibility() {
        Configuration configuration = configureHardware(false);
        ime.onConfigurationChanged(configuration);
        assertTrue(ime.onEvaluateInputViewShown());
        assertFalse(ime.hasHardwareKeyboard());
    }

    @Test
    public void disablingHideOptionTakesEffectWithoutRestart() {
        preferences.edit().putBoolean("hardware_hide_keyboard", false).apply();
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        assertTrue(ime.onEvaluateInputViewShown());
    }

    @Test
    public void interfaceLanguageChangesWithoutChangingHardwareTypingLayout() {
        AppLanguage.select(ime, "ko");
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        assertEquals("앱 언어", ime.getString(R.string.preference_app_language_title));
        assertTrue(ime.hasHardwareKeyboard());
        assertFalse(ime.onEvaluateInputViewShown());
        press(KeyEvent.KEYCODE_G, 0);
        assertEquals("d", connection.getEditable().toString());
        AppLanguage.select(ime, "en");
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        assertEquals("App language", ime.getString(R.string.preference_app_language_title));
    }

    private void startEditor(RecordingConnection target) {
        EditorInfo editor = new EditorInfo();
        editor.inputType = InputType.TYPE_CLASS_TEXT;
        editor.packageName = "example.editor";
        inputMethod.startInput(target, editor);
    }

    private void press(int keyCode, int modifiers) {
        ime.onKeyDown(keyCode, key(KeyEvent.ACTION_DOWN, keyCode, modifiers));
        ime.onKeyUp(keyCode, key(KeyEvent.ACTION_UP, keyCode, modifiers));
    }

    private static KeyEvent key(int action, int code, int modifiers) {
        return new KeyEvent(
                100,
                150,
                action,
                code,
                0,
                modifiers,
                KeyCharacterMap.VIRTUAL_KEYBOARD,
                0,
                0,
                InputDevice.SOURCE_KEYBOARD);
    }

    private static Configuration configureHardware(boolean connected) {
        Resources resources = RuntimeEnvironment.getApplication().getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.keyboard =
                connected ? Configuration.KEYBOARD_QWERTY : Configuration.KEYBOARD_NOKEYS;
        configuration.hardKeyboardHidden =
                connected
                        ? Configuration.HARDKEYBOARDHIDDEN_NO
                        : Configuration.HARDKEYBOARDHIDDEN_YES;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return configuration;
    }

    private static final class RecordingConnection extends BaseInputConnection {
        private final List<KeyEvent> events = new ArrayList<>();

        private RecordingConnection(View view) {
            super(view, true);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            events.add(event);
            return true;
        }
    }
}
