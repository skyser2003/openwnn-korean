package me.blog.hgl1002.openwnn;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.ViewConfiguration;

import me.blog.hgl1002.openwnn.preference.KeystrokePreference;

import java.util.HashMap;
import java.util.Map;

/** Owns hardware press/release pairs independently of the software keyboard view. */
public final class HardwareKeyHandler {
    public enum KeyDisposition {
        TEXT,
        PASS_THROUGH,
        CONSUMED
    }

    public interface Listener {
        void finishComposition();

        void performLanguageAction(String action);

        boolean sendKeyEvent(KeyEvent event);
    }

    private final Listener listener;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Map<Long, Press> presses = new HashMap<>();
    private KeystrokePreference.KeyStroke languageKey =
            KeystrokePreference.parseKeyStroke("---s62");
    private HardwareKeyMapper mapper = new HardwareKeyMapper("", false);
    private String shortAction = SebeolHangulIME.LANGKEY_SWITCH_KOR_ENG;
    private String longAction = SebeolHangulIME.LANGKEY_LIST_METHODS;
    private int longPressTimeout = ViewConfiguration.getLongPressTimeout();
    private boolean rightAltSymbols = true;
    private LanguagePress languagePress;

    public HardwareKeyHandler(Listener listener) {
        this.listener = listener;
    }

    public void configure(SharedPreferences preferences) {
        cancelLanguagePress();
        languageKey =
                KeystrokePreference.parseKeyStroke(
                        preferences.getString("system_hardware_lang_key_stroke", "---s62"));
        shortAction =
                preferences.getString(
                        "system_action_on_lang_key_press", SebeolHangulIME.LANGKEY_SWITCH_KOR_ENG);
        longAction =
                preferences.getString(
                        "system_action_on_lang_key_long_press",
                        SebeolHangulIME.LANGKEY_LIST_METHODS);
        longPressTimeout =
                Math.max(
                        1,
                        preferences.getInt(
                                "keyboard_long_press_timeout",
                                ViewConfiguration.getLongPressTimeout()));
        rightAltSymbols = preferences.getBoolean("hardware_alt_direct", true);
        mapper =
                new HardwareKeyMapper(
                        preferences.getString(
                                "hardware_alphabet_layout", "keyboard_alphabet_qwerty"),
                        "qwerty"
                                .equals(
                                        preferences.getString(
                                                "hardware_shortcut_layout", "layout")));
    }

    public boolean isShortcut(KeyEvent event) {
        if (KeyEvent.isModifierKey(event.getKeyCode())) return false;
        boolean altGr =
                rightAltSymbols
                        && (event.getMetaState() & KeyEvent.META_ALT_RIGHT_ON) != 0
                        && !event.isCtrlPressed()
                        && !event.isMetaPressed();
        return event.isCtrlPressed() || event.isMetaPressed() || (event.isAltPressed() && !altGr);
    }

    public KeyDisposition onKeyDown(KeyEvent event, boolean textEditor) {
        long identity = identity(event);
        Press previous = presses.get(identity);
        if (event.getRepeatCount() > 0
                && previous != null
                && previous.disposition != KeyDisposition.TEXT) {
            if (previous.forwarded)
                listener.sendKeyEvent(HardwareKeyMapper.withKeyCode(event, previous.keyCode));
            return previous.disposition;
        }
        presses.remove(identity);
        if (languageKey.matches(event)) {
            if (event.getRepeatCount() == 0) {
                cancelLanguagePress();
                listener.finishComposition();
                presses.put(
                        identity, new Press(event.getKeyCode(), KeyDisposition.CONSUMED, false));
                languagePress =
                        new LanguagePress(identity, event.getDeviceId(), shortAction, longAction);
                handler.postDelayed(languagePress, longPressTimeout);
            }
            return KeyDisposition.CONSUMED;
        }
        if (!KeyEvent.isModifierKey(event.getKeyCode()) || !languageKey.matchesModifiers(event))
            cancelLanguagePress();
        if (textEditor && !isShortcut(event)) return KeyDisposition.TEXT;
        if (KeyEvent.isModifierKey(event.getKeyCode())) return KeyDisposition.PASS_THROUGH;
        listener.finishComposition();
        int mappedCode = mapper.mapKeyCode(event.getKeyCode());
        if (mappedCode == event.getKeyCode()
                || !listener.sendKeyEvent(HardwareKeyMapper.withKeyCode(event, mappedCode))) {
            // Remember unchanged presses too: a focus/layout change during a hold must not
            // turn subsequent repeats into text or remap only the release.
            presses.put(
                    identity, new Press(event.getKeyCode(), KeyDisposition.PASS_THROUGH, false));
            return KeyDisposition.PASS_THROUGH;
        }
        presses.put(identity, new Press(mappedCode, KeyDisposition.CONSUMED, true));
        return KeyDisposition.CONSUMED;
    }

    public void consume(KeyEvent event) {
        presses.put(identity(event), new Press(event.getKeyCode(), KeyDisposition.TEXT, false));
    }

    public boolean onKeyUp(KeyEvent event) {
        Press press = presses.remove(identity(event));
        if (languagePress != null
                && languagePress.deviceId == event.getDeviceId()
                && (languagePress.identity == identity(event)
                        || (KeyEvent.isModifierKey(event.getKeyCode())
                                && !languageKey.matchesModifiers(event)))) {
            LanguagePress completed = languagePress;
            languagePress = null;
            handler.removeCallbacks(completed);
            if (!completed.performed && !event.isCanceled())
                listener.performLanguageAction(completed.shortAction);
        }
        if (press == null) return false;
        if (press.forwarded)
            listener.sendKeyEvent(HardwareKeyMapper.withKeyCode(event, press.keyCode));
        return press.disposition != KeyDisposition.PASS_THROUGH;
    }

    public void cancelLanguagePress() {
        if (languagePress != null) handler.removeCallbacks(languagePress);
        languagePress = null;
    }

    public void reset() {
        cancelLanguagePress();
        presses.clear();
    }

    private static long identity(KeyEvent event) {
        return ((long) event.getDeviceId() << 32) | (event.getKeyCode() & 0xffffffffL);
    }

    private static final class Press {
        private final int keyCode;
        private final KeyDisposition disposition;
        private final boolean forwarded;

        private Press(int keyCode, KeyDisposition disposition, boolean forwarded) {
            this.keyCode = keyCode;
            this.disposition = disposition;
            this.forwarded = forwarded;
        }
    }

    private final class LanguagePress implements Runnable {
        private final long identity;
        private final int deviceId;
        private final String shortAction;
        private final String longAction;
        private boolean performed;

        private LanguagePress(long identity, int deviceId, String shortAction, String longAction) {
            this.identity = identity;
            this.deviceId = deviceId;
            this.shortAction = shortAction;
            this.longAction = longAction;
        }

        @Override
        public void run() {
            if (languagePress != this) return;
            performed = true;
            listener.performLanguageAction(longAction);
        }
    }
}
