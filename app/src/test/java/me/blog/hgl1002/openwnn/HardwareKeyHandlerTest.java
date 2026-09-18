package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.SharedPreferences;
import android.os.Looper;
import android.view.InputDevice;
import android.view.KeyEvent;

import androidx.preference.PreferenceManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.LooperMode;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
@LooperMode(LooperMode.Mode.PAUSED)
public class HardwareKeyHandlerTest {
    private SharedPreferences preferences;
    private RecordingListener listener;
    private HardwareKeyHandler keys;

    @Before
    public void setUp() {
        preferences =
                PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.getApplication());
        preferences
                .edit()
                .clear()
                .putString("hardware_alphabet_layout", "keyboard_alphabet_colemak")
                .putInt("keyboard_long_press_timeout", 500)
                .apply();
        listener = new RecordingListener();
        keys = new HardwareKeyHandler(listener);
        keys.configure(preferences);
    }

    @Test
    public void colemakAltDForwardsMatchingPressAndReleaseWithoutText() {
        KeyEvent down = event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_G, KeyEvent.META_ALT_ON);
        assertTrue(keys.onKeyDown(down));
        assertTrue(keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_G, 0)));
        assertEquals(2, listener.events.size());
        KeyEvent forwarded = listener.events.get(0);
        assertEquals(KeyEvent.KEYCODE_D, forwarded.getKeyCode());
        assertEquals(KeyEvent.KEYCODE_D, listener.events.get(1).getKeyCode());
        assertEquals(KeyEvent.ACTION_UP, listener.events.get(1).getAction());
        assertEquals(down.getMetaState(), forwarded.getMetaState());
        assertEquals(down.getDownTime(), forwarded.getDownTime());
        assertEquals(down.getDeviceId(), forwarded.getDeviceId());
        assertEquals(down.getScanCode(), forwarded.getScanCode());
        assertEquals(down.getSource(), forwarded.getSource());
        assertEquals(1, listener.compositionFinishes);
        assertTrue(listener.actions.isEmpty());
    }

    @Test
    public void qwertyOptionPassesThroughOriginalShortcuts() {
        preferences.edit().putString("hardware_shortcut_layout", "qwerty").apply();
        keys.configure(preferences);
        assertFalse(
                keys.onKeyDown(
                        event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_D, KeyEvent.META_ALT_ON)));
        assertTrue(listener.events.isEmpty());
        assertEquals(1, listener.compositionFinishes);
    }

    @Test
    public void ctrlShiftAndMetaShortcutsUseAlphabetMapping() {
        int modifiers = KeyEvent.META_CTRL_ON | KeyEvent.META_SHIFT_ON | KeyEvent.META_META_ON;
        assertTrue(keys.onKeyDown(event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_K, modifiers)));
        assertEquals(KeyEvent.KEYCODE_E, listener.events.get(0).getKeyCode());
        assertEquals(modifiers, listener.events.get(0).getMetaState());
    }

    @Test
    public void dvorakMapsPunctuationAndKeepsShift() {
        HardwareShortcutMapper mapper =
                new HardwareShortcutMapper("keyboard_alphabet_dvorak", false);
        assertEquals(KeyEvent.KEYCODE_APOSTROPHE, mapper.mapKeyCode(KeyEvent.KEYCODE_Q));
        assertEquals(KeyEvent.KEYCODE_S, mapper.mapKeyCode(KeyEvent.KEYCODE_SEMICOLON));
        assertEquals(KeyEvent.KEYCODE_LEFT_BRACKET, mapper.mapKeyCode(KeyEvent.KEYCODE_MINUS));
        assertEquals(KeyEvent.KEYCODE_F1, mapper.mapKeyCode(KeyEvent.KEYCODE_F1));
    }

    @Test
    public void ordinaryTypingRepeatsAndOverlappingReleasesStayIndependent() {
        KeyEvent a = event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_A, 0);
        keys.consume(a);
        assertFalse(keys.onKeyDown(KeyEvent.changeTimeRepeat(a, 200, 1)));
        keys.consume(a);
        assertFalse(
                keys.onKeyDown(
                        event(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_CTRL_LEFT,
                                KeyEvent.META_CTRL_ON)));
        assertTrue(
                keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_A, KeyEvent.META_CTRL_ON)));
        assertFalse(keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_CTRL_LEFT, 0)));
    }

    @Test
    public void shortLanguagePressRunsConfiguredActionOnlyOnRelease() {
        preferences
                .edit()
                .putString("system_action_on_lang_key_press", SebeolHangulIME.LANGKEY_OPEN_SETTINGS)
                .apply();
        keys.configure(preferences);
        assertTrue(
                keys.onKeyDown(
                        event(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_SPACE,
                                KeyEvent.META_SHIFT_ON)));
        advance(200);
        assertTrue(listener.actions.isEmpty());
        assertTrue(
                keys.onKeyUp(
                        event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON)));
        advance(1000);
        assertEquals(1, listener.actions.size());
        assertEquals(SebeolHangulIME.LANGKEY_OPEN_SETTINGS, listener.actions.get(0));
    }

    @Test
    public void holdingLanguageCombinationRunsLongActionOnceWithoutShortActionOrSpace() {
        KeyEvent down = event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON);
        assertTrue(keys.onKeyDown(down));
        advance(501);
        assertTrue(keys.onKeyDown(KeyEvent.changeTimeRepeat(down, 600, 1)));
        assertTrue(keys.onKeyDown(KeyEvent.changeTimeRepeat(down, 700, 2)));
        assertTrue(keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SPACE, 0)));
        advance(1000);
        assertEquals(1, listener.actions.size());
        assertEquals(SebeolHangulIME.LANGKEY_LIST_METHODS, listener.actions.get(0));
        assertTrue(listener.events.isEmpty());
    }

    @Test
    public void ctrlSpaceBindingTakesPriorityOverAppShortcuts() {
        preferences.edit().putString("system_hardware_lang_key_stroke", "c---62").apply();
        keys.configure(preferences);
        assertTrue(
                keys.onKeyDown(
                        event(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_SPACE,
                                KeyEvent.META_CTRL_ON)));
        assertTrue(
                keys.onKeyUp(
                        event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SPACE, KeyEvent.META_CTRL_ON)));
        assertEquals(SebeolHangulIME.LANGKEY_SWITCH_KOR_ENG, listener.actions.get(0));
        assertTrue(listener.events.isEmpty());
    }

    @Test
    public void releasingModifierFirstCompletesShortActionOnce() {
        keys.onKeyDown(event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON));
        advance(100);
        assertFalse(keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SHIFT_LEFT, 0)));
        advance(600);
        assertTrue(keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SPACE, 0)));
        assertEquals(1, listener.actions.size());
        assertEquals(SebeolHangulIME.LANGKEY_SWITCH_KOR_ENG, listener.actions.get(0));
    }

    @Test
    public void leavingEditorCancelsPendingActionButKeepsConsumedRelease() {
        keys.onKeyDown(event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE, KeyEvent.META_SHIFT_ON));
        keys.cancelLanguagePress();
        advance(600);
        assertTrue(keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SPACE, 0)));
        assertTrue(listener.actions.isEmpty());
    }

    @Test
    public void layoutChangeDuringShortcutKeepsOriginalReleaseMapping() {
        keys.onKeyDown(event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_G, KeyEvent.META_ALT_ON));
        preferences.edit().putString("hardware_shortcut_layout", "qwerty").apply();
        keys.configure(preferences);
        keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_G, 0));
        assertEquals(KeyEvent.KEYCODE_D, listener.events.get(1).getKeyCode());
    }

    @Test
    public void rightAltSymbolsDoNotInterceptLeftAltShortcuts() {
        assertFalse(
                keys.isShortcut(
                        event(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_G,
                                KeyEvent.META_ALT_ON | KeyEvent.META_ALT_RIGHT_ON)));
        assertTrue(
                keys.isShortcut(
                        event(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_G,
                                KeyEvent.META_ALT_ON | KeyEvent.META_ALT_LEFT_ON)));
        assertTrue(
                keys.isShortcut(
                        event(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_G,
                                KeyEvent.META_CTRL_ON
                                        | KeyEvent.META_ALT_ON
                                        | KeyEvent.META_ALT_RIGHT_ON)));
    }

    @Test
    public void failedForwardingLeavesOriginalEventAvailableToApplication() {
        listener.acceptEvents = false;
        assertFalse(
                keys.onKeyDown(
                        event(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_G, KeyEvent.META_ALT_ON)));
        assertFalse(keys.onKeyUp(event(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_G, 0)));
    }

    private static KeyEvent event(int action, int code, int modifiers) {
        return new KeyEvent(
                100, 150, action, code, 0, modifiers, 7, 32, 0, InputDevice.SOURCE_KEYBOARD);
    }

    private static void advance(int milliseconds) {
        Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(milliseconds));
    }

    private static final class RecordingListener implements HardwareKeyHandler.Listener {
        private final List<KeyEvent> events = new ArrayList<>();
        private final List<String> actions = new ArrayList<>();
        private int compositionFinishes;
        private boolean acceptEvents = true;

        @Override
        public void finishComposition() {
            compositionFinishes++;
        }

        @Override
        public void performLanguageAction(String action) {
            actions.add(action);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            events.add(event);
            return acceptEvents;
        }
    }
}
