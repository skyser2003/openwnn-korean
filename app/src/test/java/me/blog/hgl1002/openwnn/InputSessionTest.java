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
import android.view.inputmethod.InputBinding;
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

    @Test
    public void qwertyCanvasKeysPassThroughWithoutTextConversion() {
        selectNonTextLayout("qwerty");
        RecordingConnection canvas = startCanvas();
        for (int modifiers : new int[] {0, KeyEvent.META_SHIFT_ON}) {
            assertFalse(
                    ime.onKeyDown(
                            KeyEvent.KEYCODE_G,
                            key(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_G, modifiers)));
            assertFalse(
                    ime.onKeyUp(
                            KeyEvent.KEYCODE_G,
                            key(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_G, modifiers)));
        }
        assertEquals(0, canvas.textCommits);
        assertTrue(canvas.events.isEmpty());
    }

    @Test
    public void canvasKeysFollowAlphabetLayoutAsPairedKeyEvents() {
        RecordingConnection canvas = startCanvas();
        press(KeyEvent.KEYCODE_G, 0);
        press(KeyEvent.KEYCODE_K, KeyEvent.META_SHIFT_ON);
        assertEquals(0, canvas.textCommits);
        assertEquals(4, canvas.events.size());
        assertEquals(KeyEvent.KEYCODE_D, canvas.events.get(0).getKeyCode());
        assertEquals(KeyEvent.KEYCODE_D, canvas.events.get(1).getKeyCode());
        assertEquals(KeyEvent.ACTION_DOWN, canvas.events.get(0).getAction());
        assertEquals(KeyEvent.ACTION_UP, canvas.events.get(1).getAction());
        assertEquals(KeyEvent.KEYCODE_E, canvas.events.get(2).getKeyCode());
        assertEquals(KeyEvent.META_SHIFT_ON, canvas.events.get(2).getMetaState());
        assertEquals(KeyEvent.KEYCODE_E, canvas.events.get(3).getKeyCode());
    }

    @Test
    public void dvorakCanvasPunctuationUsesKeyEvents() {
        preferences
                .edit()
                .putString("hardware_alphabet_layout", "keyboard_alphabet_dvorak")
                .apply();
        Shadows.shadowOf(Looper.getMainLooper()).idle();
        RecordingConnection canvas = startCanvas();
        press(KeyEvent.KEYCODE_Q, KeyEvent.META_SHIFT_ON);
        assertEquals(0, canvas.textCommits);
        assertEquals(2, canvas.events.size());
        assertEquals(KeyEvent.KEYCODE_APOSTROPHE, canvas.events.get(0).getKeyCode());
        assertEquals(KeyEvent.META_SHIFT_ON, canvas.events.get(0).getMetaState());
        assertEquals(KeyEvent.KEYCODE_APOSTROPHE, canvas.events.get(1).getKeyCode());
    }

    @Test
    public void canvasControlsStayRawUnderBothLayouts() {
        RecordingConnection canvas = startCanvas();
        for (String layout : new String[] {"layout", "qwerty"}) {
            selectNonTextLayout(layout);
            for (int code :
                    new int[] {
                        KeyEvent.KEYCODE_SPACE, KeyEvent.KEYCODE_ENTER, KeyEvent.KEYCODE_TAB,
                        KeyEvent.KEYCODE_DEL, KeyEvent.KEYCODE_DPAD_LEFT, KeyEvent.KEYCODE_F1
                    }) {
                assertFalse(ime.onKeyDown(code, key(KeyEvent.ACTION_DOWN, code, 0)));
                assertFalse(ime.onKeyUp(code, key(KeyEvent.ACTION_UP, code, 0)));
            }
        }
        assertEquals(0, canvas.textCommits);
        assertTrue(canvas.events.isEmpty());
    }

    @Test
    public void hiddenTextEditorStillTypesColemakUnderBothNonTextLayouts() {
        for (String layout : new String[] {"layout", "qwerty"}) {
            selectNonTextLayout(layout);
            press(KeyEvent.KEYCODE_G, 0);
            press(KeyEvent.KEYCODE_G, KeyEvent.META_SHIFT_ON);
        }
        assertEquals("dDdD", connection.getEditable().toString());
        assertTrue(connection.events.isEmpty());
    }

    @Test
    public void qwertyNonTextLayoutPreservesHangulTextEntry() {
        selectNonTextLayout("qwerty");
        press(KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON);
        press(KeyEvent.KEYCODE_R, 0);
        press(KeyEvent.KEYCODE_K, 0);
        assertEquals("가", connection.getEditable().toString());
        assertTrue(connection.events.isEmpty());
    }

    @Test
    public void canvasUsesAlphabetKeysWhileKoreanModeIsSelected() {
        press(KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON);
        RecordingConnection canvas = startCanvas();
        press(KeyEvent.KEYCODE_G, 0);
        assertEquals(2, canvas.events.size());
        assertEquals(KeyEvent.KEYCODE_D, canvas.events.get(0).getKeyCode());
        selectNonTextLayout("qwerty");
        assertFalse(
                ime.onKeyDown(
                        KeyEvent.KEYCODE_G, key(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_G, 0)));
        assertFalse(
                ime.onKeyUp(KeyEvent.KEYCODE_G, key(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_G, 0)));
        assertEquals(0, canvas.textCommits);
        assertEquals(2, canvas.events.size());
    }

    @Test
    public void heldCanvasKeyDoesNotBecomeTextWhenEditorReceivesFocus() {
        selectNonTextLayout("qwerty");
        RecordingConnection canvas = startCanvas();
        KeyEvent down = key(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_G, 0);
        assertFalse(ime.onKeyDown(down.getKeyCode(), down));
        startEditor(connection);
        KeyEvent repeat = KeyEvent.changeTimeRepeat(down, 200, 1);
        assertFalse(ime.onKeyDown(repeat.getKeyCode(), repeat));
        assertFalse(
                ime.onKeyUp(KeyEvent.KEYCODE_G, key(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_G, 0)));
        assertEquals(0, canvas.textCommits);
        assertEquals(0, connection.textCommits);
        assertTrue(canvas.events.isEmpty());
        assertTrue(connection.events.isEmpty());
        press(KeyEvent.KEYCODE_G, 0);
        assertEquals("d", connection.getEditable().toString());
    }

    @Test
    public void fallbackConnectionWithoutStartedEditorUsesNonTextLayout() {
        RecordingConnection canvas = new RecordingConnection(new View(ime), false);
        inputMethod.bindInput(new InputBinding(canvas, null, 0, 0));
        ((InputMethodService.InputMethodSessionImpl) ime.onCreateInputMethodSessionInterface())
                .finishInput();
        assertFalse(ime.getCurrentInputStarted());
        assertSame(canvas, ime.getCurrentInputConnection());
        press(KeyEvent.KEYCODE_G, 0);
        assertEquals(0, canvas.textCommits);
        assertEquals(2, canvas.events.size());
        assertEquals(KeyEvent.KEYCODE_D, canvas.events.get(0).getKeyCode());
        assertEquals(KeyEvent.KEYCODE_D, canvas.events.get(1).getKeyCode());
    }

    private void selectNonTextLayout(String layout) {
        preferences.edit().putString("hardware_shortcut_layout", layout).apply();
        Shadows.shadowOf(Looper.getMainLooper()).idle();
    }

    private RecordingConnection startCanvas() {
        RecordingConnection canvas = new RecordingConnection(new View(ime), false);
        startEditor(canvas, InputType.TYPE_NULL);
        return canvas;
    }

    private void startEditor(RecordingConnection target) {
        startEditor(target, InputType.TYPE_CLASS_TEXT);
    }

    private void startEditor(RecordingConnection target, int inputType) {
        EditorInfo editor = new EditorInfo();
        editor.inputType = inputType;
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
        private int textCommits;

        private RecordingConnection(View view) {
            this(view, true);
        }

        private RecordingConnection(View view, boolean fullEditor) {
            super(view, fullEditor);
        }

        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {
            textCommits++;
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            events.add(event);
            return true;
        }
    }
}
