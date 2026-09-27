package me.blog.hgl1002.openwnn.preference;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.preference.Preference;

import me.blog.hgl1002.openwnn.HardwareKeyMapper;
import me.blog.hgl1002.openwnn.R;

public class KeystrokePreference extends Preference {
	private String defaultStroke = "---s62";

	public KeystrokePreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public KeystrokePreference(Context context) {
		super(context);
	}

	@Override
	protected void onClick() {
		View view = View.inflate(getContext(), R.layout.select_keystroke, null);
		TextView display = view.findViewById(R.id.key_display);
		TextView description = view.findViewById(R.id.key_description);
		description.setText(getSummary());
		CheckBox control = view.findViewById(R.id.key_control);
		CheckBox alt = view.findViewById(R.id.key_alt);
		CheckBox win = view.findViewById(R.id.key_win);
		CheckBox shift = view.findViewById(R.id.key_shift);
		Capture capture = new Capture(parseKeyStroke(getPersistedString(defaultStroke)));
		KeyStroke stroke = capture.getStroke();
		control.setChecked(stroke.isControl());
		alt.setChecked(stroke.isAlt());
		win.setChecked(stroke.isWin());
		shift.setChecked(stroke.isShift());
		display.setText(KeyEvent.keyCodeToString(stroke.getKeyCode()));
		new AlertDialog.Builder(getContext())
				.setTitle(getTitle())
				.setView(view)
				.setOnKeyListener(
						(dialog, keyCode, event) -> {
							if (keyCode == KeyEvent.KEYCODE_BACK) return false;
							if (capture.accept(event)) {
								KeyStroke captured = capture.getStroke();
								display.setText(KeyEvent.keyCodeToString(captured.getKeyCode()));
								control.setChecked(captured.isControl());
								alt.setChecked(captured.isAlt());
								win.setChecked(captured.isWin());
								shift.setChecked(captured.isShift());
							}
							return true;
						})
				.setPositiveButton(
						R.string.dialog_button_ok,
						(dialog, which) -> {
							String value =
									new KeyStroke(
													control.isChecked(),
													alt.isChecked(),
													win.isChecked(),
													shift.isChecked(),
													capture.getStroke().getKeyCode())
											.serialize();
							if (callChangeListener(value)) {
								persistString(value);
								notifyChanged();
							}
						})
				.setNegativeButton(R.string.dialog_button_cancel, null)
				.show();
	}

	public static KeyStroke parseKeyStroke(String value) {
		if (value != null && value.matches("[c-][a-][w-][s-][0-9]+")) {
			try {
				int code = Integer.parseInt(value.substring(4));
				if (code > KeyEvent.KEYCODE_UNKNOWN && code <= KeyEvent.getMaxKeyCode()) {
					return new KeyStroke(
							value.charAt(0) == 'c',
							value.charAt(1) == 'a',
							value.charAt(2) == 'w',
							value.charAt(3) == 's',
							code);
				}
			} catch (NumberFormatException invalidKeyCode) {
				// Restore the default when an old or corrupted preference cannot be decoded.
			}
		}
		return new KeyStroke(false, false, false, true, KeyEvent.KEYCODE_SPACE);
	}

	@Override
	protected String onGetDefaultValue(TypedArray attributes, int index) {
		defaultStroke = attributes.getString(index);
		return defaultStroke;
	}

	public static final class Capture {
		private KeyStroke stroke;
		private boolean capturedPrimary;

		public Capture(KeyStroke initial) {
			stroke = initial;
		}

		public boolean accept(KeyEvent event) {
			if (event.getAction() != KeyEvent.ACTION_DOWN || event.getRepeatCount() != 0)
				return false;
			boolean modifier = KeyEvent.isModifierKey(event.getKeyCode());
			if (modifier && capturedPrimary) return false;
			stroke = KeyStroke.fromEvent(event);
			capturedPrimary = !modifier;
			return true;
		}

		public KeyStroke getStroke() {
			return stroke;
		}
	}

	public static final class KeyStroke {
		private final boolean control;
		private final boolean alt;
		private final boolean win;
		private final boolean shift;
		private final int keyCode;

		public KeyStroke(boolean control, boolean alt, boolean win, boolean shift, int keyCode) {
			this.control = control;
			this.alt = alt;
			this.win = win;
			this.shift = shift;
			this.keyCode = keyCode;
		}

		public static KeyStroke fromEvent(KeyEvent event) {
			int code = event.getKeyCode();
			return new KeyStroke(
					event.isCtrlPressed()
							&& code != KeyEvent.KEYCODE_CTRL_LEFT
							&& code != KeyEvent.KEYCODE_CTRL_RIGHT,
					event.isAltPressed()
							&& code != KeyEvent.KEYCODE_ALT_LEFT
							&& code != KeyEvent.KEYCODE_ALT_RIGHT,
					event.isMetaPressed()
							&& code != KeyEvent.KEYCODE_META_LEFT
							&& code != KeyEvent.KEYCODE_META_RIGHT,
					event.isShiftPressed()
							&& code != KeyEvent.KEYCODE_SHIFT_LEFT
							&& code != KeyEvent.KEYCODE_SHIFT_RIGHT,
					code);
		}

		public boolean matches(KeyEvent event) {
			return event.getKeyCode() == keyCode && matchesModifiers(event);
		}

		public boolean matchesModifiers(KeyEvent event) {
			KeyStroke modifiers = fromEvent(HardwareKeyMapper.withKeyCode(event, keyCode));
			return control == modifiers.control
					&& alt == modifiers.alt
					&& win == modifiers.win
					&& shift == modifiers.shift;
		}

		public String serialize() {
			return (control ? "c" : "-")
					+ (alt ? "a" : "-")
					+ (win ? "w" : "-")
					+ (shift ? "s" : "-")
					+ keyCode;
		}

		public boolean isControl() {
			return control;
		}

		public boolean isAlt() {
			return alt;
		}

		public boolean isWin() {
			return win;
		}

		public boolean isShift() {
			return shift;
		}

		public int getKeyCode() {
			return keyCode;
		}
	}
}
