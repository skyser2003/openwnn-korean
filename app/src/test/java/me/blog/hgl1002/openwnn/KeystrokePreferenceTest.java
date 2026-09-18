package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.view.KeyEvent;

import me.blog.hgl1002.openwnn.preference.KeystrokePreference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class KeystrokePreferenceTest {
    @Test
    public void modifierReleaseCannotOverwriteCapturedCombination() {
        KeystrokePreference.Capture capture =
                new KeystrokePreference.Capture(KeystrokePreference.parseKeyStroke("---s62"));
        assertTrue(
                capture.accept(
                        key(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_CTRL_LEFT,
                                KeyEvent.META_CTRL_ON)));
        assertTrue(
                capture.accept(
                        key(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE, KeyEvent.META_CTRL_ON)));
        assertFalse(
                capture.accept(
                        key(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_SPACE, KeyEvent.META_CTRL_ON)));
        assertFalse(capture.accept(key(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_CTRL_LEFT, 0)));
        assertEquals("c---62", capture.getStroke().serialize());
    }

    @Test
    public void invalidStoredBindingsFallBackToShiftSpace() {
        for (String value :
                new String[] {"", "x", "----0", "----bad", "----99999999999999", "z---62"}) {
            assertEquals("---s62", KeystrokePreference.parseKeyStroke(value).serialize());
        }
        assertEquals("---s62", KeystrokePreference.parseKeyStroke(null).serialize());
    }

    @Test
    public void capsLockDoesNotPreventShiftSpaceAndExtraModifiersDoNotMatch() {
        KeystrokePreference.KeyStroke stroke = KeystrokePreference.parseKeyStroke("---s62");
        assertTrue(
                stroke.matches(
                        key(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_SPACE,
                                KeyEvent.META_SHIFT_ON | KeyEvent.META_CAPS_LOCK_ON)));
        assertFalse(
                stroke.matches(
                        key(
                                KeyEvent.ACTION_DOWN,
                                KeyEvent.KEYCODE_SPACE,
                                KeyEvent.META_SHIFT_ON | KeyEvent.META_CTRL_ON)));
    }

    @Test
    public void modifierKeyCanBeAssignedAlone() {
        KeyEvent shift =
                key(
                        KeyEvent.ACTION_DOWN,
                        KeyEvent.KEYCODE_SHIFT_RIGHT,
                        KeyEvent.META_SHIFT_ON | KeyEvent.META_SHIFT_RIGHT_ON);
        KeystrokePreference.KeyStroke stroke = KeystrokePreference.KeyStroke.fromEvent(shift);
        assertFalse(stroke.isShift());
        assertTrue(stroke.matches(shift));
    }

    private static KeyEvent key(int action, int code, int modifiers) {
        return new KeyEvent(100, 150, action, code, 0, modifiers);
    }
}
