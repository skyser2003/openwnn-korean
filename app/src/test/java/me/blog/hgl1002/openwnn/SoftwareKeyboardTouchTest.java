package me.blog.hgl1002.openwnn;

import static org.junit.Assert.*;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Looper;
import android.os.SystemClock;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.EditorInfo;

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

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {28, 33, 35})
@LooperMode(LooperMode.Mode.PAUSED)
public class SoftwareKeyboardTouchTest {
    private ServiceController<SebeolHangulIME> controller;
    private SebeolHangulIME ime;
    private BaseInputConnection connection;
    private EditorInfo editor;
    private KeyboardView keyboard;
    private long downTime;

    @Before
    public void setUp() {
        PreferenceManager.getDefaultSharedPreferences(RuntimeEnvironment.getApplication())
                .edit()
                .clear()
                .putBoolean("initial_launch", false)
                .putBoolean("popup_preview", false)
                .putBoolean("keyboard_use_moachigi", false)
                .apply();
        Resources resources = RuntimeEnvironment.getApplication().getResources();
        Configuration configuration = new Configuration(resources.getConfiguration());
        configuration.keyboard = Configuration.KEYBOARD_NOKEYS;
        configuration.hardKeyboardHidden = Configuration.HARDKEYBOARDHIDDEN_YES;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        controller = Robolectric.buildService(SebeolHangulIME.class).create();
        ime = controller.get();
        connection = new BaseInputConnection(new View(ime), true);
        editor = new EditorInfo();
        editor.inputType = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT;
        editor.packageName = "example.browser";
        InputMethodService.InputMethodImpl inputMethod =
                (InputMethodService.InputMethodImpl) ime.onCreateInputMethodInterface();
        inputMethod.startInput(connection, editor);
        ime.onStartInputView(editor, false);
        keyboard = ime.onCreateInputView().findViewById(R.id.keyboard);
    }

    @After
    public void tearDown() {
        controller.destroy();
    }

    @Test
    public void singleTapsWorkAfterHidingAndReopeningTheSameEditor() {
        tap('a');
        assertEquals("a", connection.getEditable().toString());
        for (int cycle = 0; cycle < 3; cycle++) {
            ime.onFinishInputView(false);
            ime.hideWindow();
            // Reopening the keyboard need not create a new input session.
            ime.onStartInputView(editor, false);
            tap('b');
        }
        assertEquals("abbb", connection.getEditable().toString());
    }

    @Test
    public void longPressAfterReopeningRunsOnceWithoutAnExtraTap() {
        ime.hideWindow();
        ime.onStartInputView(editor, false);
        touch(MotionEvent.ACTION_DOWN, 'a');
        idle(700);
        touch(MotionEvent.ACTION_UP, 'a');
        assertEquals("A", connection.getEditable().toString());
        tap('b');
        assertEquals("Ab", connection.getEditable().toString());
    }

    @Test
    public void hidingKeyboardCancelsPendingLongPress() {
        touch(MotionEvent.ACTION_DOWN, 'a');
        ime.hideWindow();
        idle(700);
        assertEquals("", connection.getEditable().toString());
        ime.onStartInputView(editor, false);
        tap('b');
        assertEquals("b", connection.getEditable().toString());
    }

    @Test
    public void cancelledTouchDoesNotTypeOrFireLongPress() {
        touch(MotionEvent.ACTION_DOWN, 'a');
        touch(MotionEvent.ACTION_CANCEL, 'a');
        idle(700);
        assertEquals("", connection.getEditable().toString());
        tap('b');
        assertEquals("b", connection.getEditable().toString());
    }

    @Test
    public void finishingInputViewCancelsItsPendingTouches() {
        touch(MotionEvent.ACTION_DOWN, 'a');
        ime.onFinishInputView(false);
        idle(700);
        assertEquals("", connection.getEditable().toString());
        ime.onStartInputView(editor, false);
        tap('b');
        assertEquals("b", connection.getEditable().toString());
    }

    private void tap(int code) {
        touch(MotionEvent.ACTION_DOWN, code);
        touch(MotionEvent.ACTION_UP, code);
    }

    private void touch(int action, int code) {
        Keyboard.Key key =
                keyboard.getKeyboard().getKeys().stream()
                        .filter(candidate -> candidate.codes[0] == code)
                        .findFirst()
                        .orElseThrow(() -> new AssertionError("Missing key: " + code));
        if (action == MotionEvent.ACTION_DOWN) downTime = SystemClock.uptimeMillis();
        MotionEvent event =
                MotionEvent.obtain(
                        downTime,
                        SystemClock.uptimeMillis(),
                        action,
                        key.x + key.width / 2f,
                        key.y + key.height / 2f,
                        0);
        keyboard.dispatchTouchEvent(event);
        event.recycle();
    }

    private static void idle(long milliseconds) {
        Shadows.shadowOf(Looper.getMainLooper()).idleFor(Duration.ofMillis(milliseconds));
    }
}
