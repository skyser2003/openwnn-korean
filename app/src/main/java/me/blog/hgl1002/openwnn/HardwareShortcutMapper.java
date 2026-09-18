package me.blog.hgl1002.openwnn;

import android.view.KeyEvent;

import me.blog.hgl1002.openwnn.layout.LayoutAlphabet;

/** Translates shortcut key codes using the same alphabet tables as ordinary typing. */
public final class HardwareShortcutMapper {
    private final int[][] layout;

    public HardwareShortcutMapper(String alphabetLayout, boolean qwertyShortcuts) {
        if (qwertyShortcuts) {
            layout = new int[0][];
        } else if ("keyboard_alphabet_colemak".equals(alphabetLayout)) {
            layout = LayoutAlphabet.CONVERT_ENGLISH_COLEMAK;
        } else if ("keyboard_alphabet_dvorak".equals(alphabetLayout)) {
            layout = LayoutAlphabet.CONVERT_ENGLISH_DVORAK;
        } else {
            layout = new int[0][];
        }
    }

    public int mapKeyCode(int keyCode) {
        int character = characterForKeyCode(keyCode);
        for (int[] entry : layout) {
            if (entry[0] == character) {
                return keyCodeForCharacter(entry[1], keyCode);
            }
        }
        return keyCode;
    }

    public static KeyEvent withKeyCode(KeyEvent event, int keyCode) {
        return new KeyEvent(
                event.getDownTime(),
                event.getEventTime(),
                event.getAction(),
                keyCode,
                event.getRepeatCount(),
                event.getMetaState(),
                event.getDeviceId(),
                event.getScanCode(),
                event.getFlags(),
                event.getSource());
    }

    private static int characterForKeyCode(int keyCode) {
        if (keyCode >= KeyEvent.KEYCODE_A && keyCode <= KeyEvent.KEYCODE_Z) {
            return 'a' + keyCode - KeyEvent.KEYCODE_A;
        }
        if (keyCode >= KeyEvent.KEYCODE_0 && keyCode <= KeyEvent.KEYCODE_9) {
            return '0' + keyCode - KeyEvent.KEYCODE_0;
        }
        switch (keyCode) {
            case KeyEvent.KEYCODE_COMMA:
                return ',';
            case KeyEvent.KEYCODE_PERIOD:
                return '.';
            case KeyEvent.KEYCODE_GRAVE:
                return '`';
            case KeyEvent.KEYCODE_MINUS:
                return '-';
            case KeyEvent.KEYCODE_EQUALS:
                return '=';
            case KeyEvent.KEYCODE_LEFT_BRACKET:
                return '[';
            case KeyEvent.KEYCODE_RIGHT_BRACKET:
                return ']';
            case KeyEvent.KEYCODE_BACKSLASH:
                return '\\';
            case KeyEvent.KEYCODE_SEMICOLON:
                return ';';
            case KeyEvent.KEYCODE_APOSTROPHE:
                return '\'';
            case KeyEvent.KEYCODE_SLASH:
                return '/';
            default:
                return 0;
        }
    }

    private static int keyCodeForCharacter(int character, int fallback) {
        if (character >= 'a' && character <= 'z') {
            return KeyEvent.KEYCODE_A + character - 'a';
        }
        for (int code = KeyEvent.KEYCODE_0; code <= KeyEvent.KEYCODE_SLASH; code++) {
            if (characterForKeyCode(code) == character) return code;
        }
        return fallback;
    }
}
