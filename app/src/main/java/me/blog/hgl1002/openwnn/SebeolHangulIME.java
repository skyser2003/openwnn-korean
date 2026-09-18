package me.blog.hgl1002.openwnn;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.text.method.MetaKeyKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowInsets;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import androidx.preference.PreferenceManager;

import me.blog.hgl1002.openwnn.event.CommitComposingTextEvent;
import me.blog.hgl1002.openwnn.event.EngineModeChangeEvent;
import me.blog.hgl1002.openwnn.event.InputCharEvent;
import me.blog.hgl1002.openwnn.event.InputKeyEvent;
import me.blog.hgl1002.openwnn.event.InputSoftKeyEvent;
import me.blog.hgl1002.openwnn.event.InputTimeoutEvent;
import me.blog.hgl1002.openwnn.event.InputViewChangeEvent;
import me.blog.hgl1002.openwnn.event.SoftKeyFlickEvent;
import me.blog.hgl1002.openwnn.event.SoftKeyGestureEvent;
import me.blog.hgl1002.openwnn.event.SoftKeyLongPressEvent;
import me.blog.hgl1002.openwnn.hangul.EngineMode;
import me.blog.hgl1002.openwnn.hangul.HangulEngine;
import me.blog.hgl1002.openwnn.hangul.HangulEngine.FinishComposingEvent;
import me.blog.hgl1002.openwnn.hangul.HangulEngine.HangulEngineEvent;
import me.blog.hgl1002.openwnn.hangul.HangulEngine.HangulEngineListener;
import me.blog.hgl1002.openwnn.hangul.HangulEngine.SetComposingEvent;
import me.blog.hgl1002.openwnn.hangul.TwelveHangulEngine;
import me.blog.hgl1002.openwnn.view.DefaultSoftKeyboard;
import me.blog.hgl1002.openwnn.view.ListLangKeyActionDialogActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SebeolHangulIME extends InputMethodService
		implements HangulEngineListener,
				SharedPreferences.OnSharedPreferenceChangeListener,
				HardwareKeyHandler.Listener {

	/** Input view (software keyboard) */
	protected InputViewManager  mInputViewManager = null;
	/** The input connection */
	protected InputConnection mInputConnection = null;
	/** Auto hide candidate view */
	protected boolean mAutoHideMode = true;

	private View mPreparedInputView;
	private Resources mLocalizedResources;
	private SharedPreferences mPreferences;
	private final Handler mSettingsHandler = new Handler(Looper.getMainLooper());
	private final HardwareKeyHandler mHardwareKeys = new HardwareKeyHandler(this);
	private final Runnable mRefreshSettings = this::refreshSettings;
	private boolean mHideHardwareKeyboard;
	private boolean mKeyboardSuppressed;
	private boolean mRefreshingSettings;

	public static final int[][] SHIFT_CONVERT = {
			{0x60, 0x7e},
			{0x31, 0x21},
			{0x32, 0x40},
			{0x33, 0x23},
			{0x34, 0x24},
			{0x35, 0x25},
			{0x36, 0x5e},
			{0x37, 0x26},
			{0x38, 0x2a},
			{0x39, 0x28},
			{0x30, 0x29},
			{0x2d, 0x5f},
			{0x3d, 0x2b},

			{0x5b, 0x7b},
			{0x5d, 0x7d},
			{0x5c, 0x7c},

			{0x3b, 0x3a},
			{0x27, 0x22},

			{0x2c, 0x3c},
			{0x2e, 0x3e},
			{0x2f, 0x3f},
	};

	public static final int[][] FLICK_TABLE_12KEY = {
			{-201, 0x31},
			{-202, 0x32},
			{-203, 0x33},
			{-204, 0x34},
			{-205, 0x35},
			{-206, 0x36},
			{-207, 0x37},
			{-208, 0x38},
			{-209, 0x39},
			{-213, 0x2c},
			{-210, 0x30},
			{-211, 0x21},
	};

	public static final String LANGKEY_LIST_ACTIONS = "list_actions";
	public static final String LANGKEY_SWITCH_KOR_ENG = "switch_kor_eng";
	public static final String LANGKEY_SWITCH_NEXT_METHOD = "switch_next_method";
	public static final String LANGKEY_SWITCH_METHOD_MIXED = "switch_method_mixed";
	public static final String LANGKEY_LIST_METHODS = "list_methods";
	public static final String LANGKEY_TOGGLE_ONE_HAND_MODE = "toggle_one_hand_mode";
	public static final String LANGKEY_TOGGLE_12KEY_MODE = "toggle_12key_mode";
	public static final String LANGKEY_OPEN_SETTINGS = "open_settings";

	public static final String FLICK_NONE = "none";
	public static final String FLICK_SHIFT = "shift";
	public static final String FLICK_SYMBOL = "symbol";
	public static final String FLICK_SYMBOL_SHIFT = "symbol_shift";

	HangulEngine mHangulEngine;
	HangulEngine mQwertyEngine, m12keyEngine;

	int[][] mAltLayout;
	boolean mAltMode;

	int mHardShift;
	int mHardAlt;
	boolean mCapsLock;
	boolean mShiftOnCapsLock;

	boolean mShiftPressing;
	boolean mAltPressing;

	EngineMode mCurrentEngineMode;

	private static final int[] mShiftKeyToggle = {0, MetaKeyKeyListener.META_SHIFT_ON, MetaKeyKeyListener.META_CAP_LOCKED};

	private static final int[] mAltKeyToggle = {0, MetaKeyKeyListener.META_ALT_ON, MetaKeyKeyListener.META_ALT_LOCKED};

	boolean mDirectInputMode;
	boolean mEnableTimeout;

	boolean mMoachigi;
	boolean mHardwareMoachigi;
	boolean mFullMoachigi = true;
	int mMoachigiDelay;
	boolean mQuickPeriod;
	boolean mSpaceResetJohab = true;

	boolean mStandardJamo;
	String mLangKeyAction;
	String mLangKeyLongAction;
	String mAltKeyLongAction;

	private final Map<SoftKeyFlickEvent.Direction, String> flickAction;
	String mLongPressAction;

	boolean mAltDirect;

	boolean mSpace, mCharInput;
	boolean mInput;
	int mLastLanguage;

	boolean mBackspaceSelectionMode;
	int mBackspaceSelectionStart;
	int mBackspaceSelectionEnd;

	Handler mTimeOutHandler;

	private static WeakReference<SebeolHangulIME> mSelf = new WeakReference<>(null);

	public static SebeolHangulIME getInstance() {
		return mSelf.get();
	}

	public SebeolHangulIME() {
		super();
		mInputViewManager = new DefaultSoftKeyboard(this);

		mQwertyEngine = new HangulEngine();
		mQwertyEngine.setListener(this);
		mHangulEngine = mQwertyEngine;
		m12keyEngine = new TwelveHangulEngine();
		m12keyEngine.setListener(this);

		mAutoHideMode = false;

		flickAction = new HashMap <>();

	}

	@Override
	public void onCreate() {
		super.onCreate();
		mSelf = new WeakReference<>(this);
		mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		mLocalizedResources = AppLanguage.resources(this, super.getResources());
		mPreferences.registerOnSharedPreferenceChangeListener(this);
		EventBus.getDefault().register(this);
		loadPreferences();
	}

	@Override
	public Resources getResources() {
		return mLocalizedResources == null ? super.getResources() : mLocalizedResources;
	}

	public boolean hasHardwareKeyboard() {
		Configuration config = getResources().getConfiguration();
		return config.keyboard == Configuration.KEYBOARD_QWERTY
				&& config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO;
	}

	private boolean shouldHideKeyboard() {
		return mHideHardwareKeyboard && hasHardwareKeyboard();
	}

	private void loadPreferences() {
		SharedPreferences pref = mPreferences;
		if (pref == null) return;
		((DefaultSoftKeyboard) mInputViewManager).setHardKeyboardHidden(!hasHardwareKeyboard());
		mHideHardwareKeyboard = pref.getBoolean("hardware_hide_keyboard", false);
		mHardwareKeys.configure(pref);
		mAltLayout =
				EngineMode.get(pref.getString("keyboard_symbols_layout", "keyboard_symbols_a"))
						.layout;
		mMoachigi = pref.getBoolean("keyboard_use_moachigi", false);
		mHardwareMoachigi = pref.getBoolean("hardware_use_moachigi", true);
		mFullMoachigi = pref.getBoolean("hardware_full_moachigi", true);
		mMoachigiDelay = pref.getInt("hardware_full_moachigi_delay", 100);
		mQuickPeriod = pref.getBoolean("keyboard_quick_period", false);
		mSpaceResetJohab = pref.getBoolean("keyboard_space_reset_composing", true);
		mStandardJamo = pref.getBoolean("system_use_standard_jamo", false);
		mLangKeyAction = pref.getString("system_action_on_lang_key_press", LANGKEY_SWITCH_KOR_ENG);
		mLangKeyLongAction =
				pref.getString("system_action_on_lang_key_long_press", LANGKEY_LIST_METHODS);
		mAltKeyLongAction =
				pref.getString("system_action_on_alt_key_long_press", LANGKEY_LIST_ACTIONS);
		flickAction.put(
				SoftKeyFlickEvent.Direction.UP,
				pref.getString("keyboard_action_on_flick_up", FLICK_SHIFT));
		flickAction.put(
				SoftKeyFlickEvent.Direction.DOWN,
				pref.getString("keyboard_action_on_flick_down", FLICK_SYMBOL));
		flickAction.put(
				SoftKeyFlickEvent.Direction.LEFT,
				pref.getString("keyboard_action_on_flick_left", FLICK_NONE));
		flickAction.put(
				SoftKeyFlickEvent.Direction.RIGHT,
				pref.getString("keyboard_action_on_flick_right", FLICK_NONE));
		mLongPressAction = pref.getString("system_action_on_long_press", FLICK_SHIFT);
		mQwertyEngine.setMoachigi(hasHardwareKeyboard() ? mHardwareMoachigi : mMoachigi);
		m12keyEngine.setMoachigi(mMoachigi);
		mQwertyEngine.setFirstMidEnd(mStandardJamo);
		m12keyEngine.setFirstMidEnd(mStandardJamo);
		mAltDirect = pref.getBoolean("hardware_alt_direct", true);
		mLastLanguage = pref.getInt("misc_last_language", 0);
	}

	@Override
	public View onCreateInputView() {
		if (mPreparedInputView == null) {
			android.util.DisplayMetrics metrics = getResources().getDisplayMetrics();
			mPreparedInputView =
					mInputViewManager.initView(this, metrics.widthPixels, metrics.heightPixels);
		}
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) updateNavigationBar();
		return mPreparedInputView;
	}

	@Override
	public void onWindowShown() {
		super.onWindowShown();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) updateNavigationBar();
	}

	@Override
	public void onStartInputView(EditorInfo attribute, boolean restarting) {
		super.onStartInputView(attribute, restarting);
		mInputConnection = getCurrentInputConnection();
		mInputViewManager.startInputView();
		setCandidatesViewShown(false);
	}

	@Override
	public void onFinishInputView(boolean finishingInput) {
		mInputViewManager.closing();
		super.onFinishInputView(finishingInput);
	}

	@Override
	public void onStartInput(EditorInfo attribute, boolean restarting) {
		super.onStartInput(attribute, restarting);
		mInputConnection = getCurrentInputConnection();
		resetCharComposition();
		loadPreferences();
		if (!restarting) {
			mHardShift = 0;
			mHardAlt = 0;
			mCapsLock = false;
		}
		// Prepare the legacy view-backed layout manager even when its window is hidden.
		// Input state now belongs to the editor session, not onStartInputView().
		setInputView(onCreateInputView());
		mInputViewManager.setPreferences(mPreferences, attribute);
		mCharInput = false;
		if (!restarting && LANGKEY_SWITCH_METHOD_MIXED.equals(mLangKeyAction)) {
			((DefaultSoftKeyboard) mInputViewManager).setLanguage(mLastLanguage);
		}
		applyKeyboardVisibility();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences preferences, String key) {
		if (key == null || key.startsWith("misc_") || "initial_launch".equals(key)) return;
		mSettingsHandler.removeCallbacks(mRefreshSettings);
		mSettingsHandler.post(mRefreshSettings);
	}

	private void refreshSettings() {
		if (mRefreshingSettings) return;
		mRefreshingSettings = true;
		try {
			finishComposition();
			mLocalizedResources = AppLanguage.resources(this, super.getResources());
			loadPreferences();
			mInputViewManager.closing();
			mPreparedInputView = null;
			if (getCurrentInputEditorInfo() != null) {
				setInputView(onCreateInputView());
				mInputViewManager.setPreferences(mPreferences, getCurrentInputEditorInfo());
			}
			applyKeyboardVisibility();
		} finally {
			mRefreshingSettings = false;
		}
	}

	private void applyKeyboardVisibility() {
		boolean wasSuppressed = mKeyboardSuppressed;
		mKeyboardSuppressed = shouldHideKeyboard();
		updateInputViewShown();
		if (mKeyboardSuppressed) {
			super.setCandidatesViewShown(false);
			requestHideSelf(0);
		} else if (wasSuppressed
				&& getCurrentInputStarted()
				&& getCurrentInputEditorInfo() != null
				&& getCurrentInputEditorInfo().inputType != android.text.InputType.TYPE_NULL) {
			if (Build.VERSION.SDK_INT >= 28) requestShowSelf(0);
			else showWindow(true);
		}
	}

	@Override
	public boolean onShowInputRequested(int flags, boolean configChange) {
		return !shouldHideKeyboard() && super.onShowInputRequested(flags, configChange);
	}

	@Override
	public View onCreateCandidatesView() {
		return super.onCreateCandidatesView();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		mHardwareKeys.reset();
		mInputViewManager.closing();
		mPreparedInputView = null;
		mLocalizedResources = AppLanguage.resources(this, super.getResources());
		loadPreferences();
		super.onConfigurationChanged(newConfig);
		applyKeyboardVisibility();
	}

	@Override
	public void onFinishInput() {
		mInputViewManager.closing();
		mHardwareKeys.cancelLanguagePress();
		if (mTimeOutHandler != null) {
			mTimeOutHandler.removeCallbacksAndMessages(null);
			mTimeOutHandler = null;
		}
		resetCharComposition();
		mInputConnection = null;
		super.onFinishInput();
	}

	@Override
		public void onViewClicked(boolean focusChanged) {
		resetCharComposition();
		super.onViewClicked(focusChanged);
	}

	@Override
	public void onEvent(HangulEngineEvent event) {
		if(event instanceof FinishComposingEvent) {
			if(mInputConnection != null) mInputConnection.finishComposingText();
		}
		if(event instanceof SetComposingEvent) {
			SetComposingEvent composingEvent = (SetComposingEvent) event;
			if (mInputConnection != null)
				mInputConnection.setComposingText(composingEvent.getComposing(), 1);
		}
	}

	private void synchronizeHardwareModifiers(KeyEvent event) {
		mCapsLock = event.isCapsLockOn();
		mHardShift = event.isShiftPressed() ? 1 : (mCapsLock ? 2 : 0);
		mHardAlt = event.isAltPressed() ? 1 : 0;
		mShiftPressing = event.isShiftPressed();
		mAltPressing = event.isAltPressed();
		if (mPreparedInputView != null) {
			updateMetaKeyStateDisplay();
			updateNumKeyboardShiftState();
		}
	}

	@Subscribe
	public void onEngineModeChange(EngineModeChangeEvent event) {
		EngineMode mode = event.getEngineMode();

		boolean hardHidden = ((DefaultSoftKeyboard) mInputViewManager).mHardKeyboardHidden;

		mCurrentEngineMode = mode;

		if(mode == EngineMode.DIRECT) {
			mDirectInputMode = true;
			mEnableTimeout = false;
			mFullMoachigi = false;
			mAltMode = false;
			mHangulEngine = mQwertyEngine;
			mHangulEngine.setJamoTable(null);
			mHangulEngine.setCombinationTable(null);
			return;
		}

		EngineMode.Properties prop = mode.properties;

		mAltMode = prop.altMode;
		mDirectInputMode = prop.direct;
		mEnableTimeout = prop.timeout;
		mFullMoachigi =
				prop.fullMoachigi && mPreferences.getBoolean("hardware_full_moachigi", true);
		mHangulEngine = prop.twelveEngine ? m12keyEngine : mQwertyEngine;
		if(mode.jamoset != null) mHangulEngine.setJamoSet(mode.jamoset);
		else mHangulEngine.setJamoTable(mode.layout);
		mHangulEngine.setCombinationTable(mode.combination);
		if(prop.twelveEngine) {
			if(mode.addStroke != null) ((TwelveHangulEngine) m12keyEngine).setAddStrokeTable(mode.addStroke);
		}

		mQwertyEngine.setFullMoachigi(mFullMoachigi && !hardHidden);
		if(mFullMoachigi && !hardHidden) mEnableTimeout = true;

		((DefaultSoftKeyboard) mInputViewManager).updateKeyLabels();

	}

	@Subscribe
	public void onInputViewChange(InputViewChangeEvent event) {
		mInputViewManager.closing();
		mPreparedInputView = null;
		loadPreferences();
		if (getCurrentInputEditorInfo() != null) setInputView(onCreateInputView());
	}

	@Subscribe
	public void onSoftKeyGesture(SoftKeyGestureEvent event) {
		switch(event.getKeyCode()) {
		case KeyEvent.KEYCODE_DEL:
			switch(event.getType()) {
			case SLIDE_LEFT:
				if(!mBackspaceSelectionMode) {
					mBackspaceSelectionMode = true;
					mBackspaceSelectionEnd = mInputConnection.getTextBeforeCursor(Integer.MAX_VALUE, 0).length();
					mBackspaceSelectionStart = mBackspaceSelectionEnd;
					resetCharComposition();
				}
				while(true) {
					mBackspaceSelectionStart--;
					mInputConnection.setSelection(mBackspaceSelectionStart, mBackspaceSelectionEnd);
					if(mInputConnection.getTextBeforeCursor(1, 0).equals(" ")
							|| mBackspaceSelectionStart <= 0
							|| mBackspaceSelectionStart >= mBackspaceSelectionEnd) {
						break;
					}
				}
				break;

			case SLIDE_RIGHT:
				if(!mBackspaceSelectionMode) {
					break;
				}
				while(true) {
					mBackspaceSelectionStart++;
					mInputConnection.setSelection(mBackspaceSelectionStart, mBackspaceSelectionEnd);
					if(mInputConnection.getTextBeforeCursor(1, 0).equals(" ")
							|| mBackspaceSelectionStart <= 0
							|| mBackspaceSelectionStart >= mBackspaceSelectionEnd) {
						break;
					}
				}
				break;

			case RELEASE:
				if(!mBackspaceSelectionMode) {
					break;
				}
				mInputConnection.setSelection(mBackspaceSelectionEnd, mBackspaceSelectionEnd);
				mInputConnection.deleteSurroundingText(mBackspaceSelectionEnd - mBackspaceSelectionStart, 0);
				mBackspaceSelectionMode = false;
				break;

			}
			break;
		}
	}

	@Subscribe
	public void onInputTimeout(InputTimeoutEvent event) {
		if(mEnableTimeout) {
			resetCharComposition();
		}
		if(mQuickPeriod) {
			mSpace = false;
		}
	}

	@Subscribe
	public void onSoftKeyLongPress(SoftKeyLongPressEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode < 0) {
			if(keyCode <= -2000) {
				EventBus.getDefault().post(new InputSoftKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode - 500)));
			}
			switch(keyCode) {
			case me.blog.hgl1002.openwnn.DefaultSoftKeyboard.KEYCODE_QWERTY_ALT:
				onLangKey(mAltKeyLongAction);
				break;

			case me.blog.hgl1002.openwnn.DefaultSoftKeyboard.KEYCODE_CHANGE_LANG:
				onLangKey(mLangKeyLongAction);
				break;
			}
		} else {
			flickAction(mLongPressAction, keyCode);
		}
	}

	@Subscribe
	public void onSoftKeyFlick(SoftKeyFlickEvent event) {
		int keyCode = event.getKeyCode();
		if(keyCode < 0) {
			if(keyCode <= -2000) {
				EventBus.getDefault().post(new InputSoftKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, keyCode + event.getDirection().getTwelveKeyOffset())));
			} else if(event.getDirection() == SoftKeyFlickEvent.Direction.UP) {
				for(int[] item : FLICK_TABLE_12KEY) {
					if(item[0] == keyCode) {
						EventBus.getDefault().post(new InputCharEvent((char) item[1]));
						break;
					}
				}
			}
		} else {
			String action = flickAction.get(event.getDirection());
			if(action != null) {
				flickAction(action, keyCode);
			}
		}
	}

	@Subscribe
	public void onInputChar(InputCharEvent event) {
		char code = event.getCode();

		this.inputChar(code, false);

		shinShift();

		mCharInput = true;
		mInput = true;
		mSpace = false;
	}

	@Subscribe
	public void onInputKey(InputKeyEvent event) {
		KeyEvent keyEvent = event.getKeyEvent();
		synchronizeHardwareModifiers(keyEvent);
		if (KeyEvent.isModifierKey(keyEvent.getKeyCode())
				|| keyEvent.getKeyCode() == KeyEvent.KEYCODE_CAPS_LOCK) return;
		if (mInputConnection == null) return;
		((DefaultSoftKeyboard) mInputViewManager).fixHardwareLayoutState();
		event.setCancelled(processKeyEvent(keyEvent));
		shinShift();
	}

	@Subscribe
	public void onInputSoftKey(InputSoftKeyEvent event) {
		KeyEvent keyEvent = event.getKeyEvent();
		switch(keyEvent.getKeyCode()) {
		case me.blog.hgl1002.openwnn.DefaultSoftKeyboard.KEYCODE_CHANGE_LANG:
			onLangKey(mLangKeyAction);
			return;

		case KeyEvent.KEYCODE_SHIFT_LEFT:
		case KeyEvent.KEYCODE_SHIFT_RIGHT:
			switch(keyEvent.getAction()) {
			case KeyEvent.ACTION_UP:
				mHardShift = 0;
				mShiftPressing = false;
				updateMetaKeyStateDisplay();
				return;
			case KeyEvent.ACTION_DOWN:
				mHardShift = 1;
				mShiftPressing = true;
				updateMetaKeyStateDisplay();
				return;
			}

		case KeyEvent.KEYCODE_SPACE:
			// 두벌식 단모음, 천지인, 12키 알파벳 자판 등에서 스페이스바로 조합 끊기 옵션 적용시
			if(mSpaceResetJohab) {
				if(mCurrentEngineMode.properties.timeout) {
					resetCharComposition();
					return;
				}
			}
			resetCharComposition();
			if(mQuickPeriod && mSpace && mCharInput) {
				mInputConnection.deleteSurroundingText(1, 0);
				mInputConnection.commitText(". ", 1);
				mSpace = false;
				mCharInput = false;
			} else {
				mInputConnection.commitText(" ", 1);
				mSpace = true;
			}
			shinShift();
			return;
		}
		System.out.println();
		boolean ret = processKeyEvent(keyEvent);
		if(!ret && mInputConnection != null) {
			int c = keyEvent.getKeyCode();
			mInputConnection.sendKeyEvent(keyEvent);
			mInputConnection.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, c));
		}
		shinShift();
	}

	@Subscribe
	public void onCommitComposingText(CommitComposingTextEvent event) {
		finishComposition();
	}

	private void inputChar(char code, boolean direct) {
		int shift = mHardShift;

		if (code == 128) {
			code = (char) ((shift > 0) ? 0x2c : 0x2e);
			shift = 0;
			direct = true;
		}

		char originalCode = code;
		for (int[] item : SHIFT_CONVERT) {
			if(code == item[1]) {
				code = (char) item[0];
				shift = 1;
			}
		}

		if (mAltMode) {
			for(int[] item : mAltLayout) {
				code = Character.toLowerCase(code);
				if(code == item[0]) {
					resetCharComposition();
					mInputConnection.commitText(new String(new char[] {(char) (shift == 0 ? item[1] : item[2])}), 1);
					resetCharComposition();
					return;
				}
			}
		}

		if (mDirectInputMode) {
			code = originalCode;
			resetCharComposition();
			directInput(code, shift > 0);
			return;
		} else if (direct) {
			resetCharComposition();
			mInputConnection.commitText(new String(new char[] {originalCode}), 1);
			resetCharComposition();
			return;
		}

		int inputCode = mHangulEngine.inputCode(Character.toLowerCase(code), shift);
		if(inputCode != -1) {
			if(mHangulEngine.inputJamo(inputCode) == 0) {
				mInputConnection.commitText(new String(new char[] {(char) inputCode}), 1);
				resetCharComposition();
			}
		} else {
			resetCharComposition();
			if (shift > 0) {
				code = Character.toUpperCase(originalCode);
				for (int[] item : SHIFT_CONVERT) {
					if (code == item[0]) {
						code = (char) item[1];
					}
				}
			}
			mInputConnection.commitText(new String(new char[]{code}), 1);
			resetCharComposition();
		}
	}

	private void directInput(char code, boolean shift) {
		if (shift) {
			code = Character.toUpperCase(code);
			for(int[] item : SHIFT_CONVERT) {
				if(code == item[0]) {
					code = (char) item[1];
					break;
				}
			}
		}
		mInputConnection.commitText(String.valueOf(code), 1);
	}

	public void onLangKey(String action) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		IBinder token = Objects.requireNonNull(getWindow().getWindow()).getAttributes().token;

		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = pref.edit();

		switch(action) {
		case LANGKEY_LIST_ACTIONS:
			Intent intent = new Intent(this, ListLangKeyActionDialogActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		case LANGKEY_SWITCH_KOR_ENG:
			((DefaultSoftKeyboard) mInputViewManager).nextLanguage();
			break;

		case LANGKEY_SWITCH_NEXT_METHOD:
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				assert imm != null;
				if(mInput) {
					mInput = false;
					imm.switchToLastInputMethod(token);
				} else {
					imm.switchToNextInputMethod(token, false);
				}
			}
			break;

		case LANGKEY_SWITCH_METHOD_MIXED:
			assert imm != null;
			DefaultSoftKeyboard softKeyboardKOKR = (DefaultSoftKeyboard) mInputViewManager;
			if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
				if(mInput) {
					mLastLanguage = softKeyboardKOKR.getLanguageId();
				}
				softKeyboardKOKR.nextLanguage();
				if(!mInput && mLastLanguage == softKeyboardKOKR.getLanguageId()) {
					editor.putInt("misc_last_language", mLastLanguage);
					editor.apply();
					imm.switchToNextInputMethod(token, false);
				}
				mInput = false;
			}
			break;

		case LANGKEY_LIST_METHODS:
			assert imm != null;
			imm.showInputMethodPicker();
			break;

		case LANGKEY_TOGGLE_ONE_HAND_MODE:
			boolean oneHandedMode = pref.getBoolean("keyboard_one_hand", false);
			editor.putBoolean("keyboard_one_hand", !oneHandedMode);
			editor.apply();
			EventBus.getDefault().post(new InputViewChangeEvent());
			break;

		case LANGKEY_TOGGLE_12KEY_MODE:
			boolean use12key = pref.getBoolean("keyboard_hangul_use_12key", false);
			editor.putBoolean("keyboard_hangul_use_12key", !use12key);
			editor.apply();
			EventBus.getDefault().post(new InputViewChangeEvent());
			break;

		case LANGKEY_OPEN_SETTINGS:
			intent = new Intent(this, SebeolHangulIMEControlPanel.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;

		}
	}

	@SuppressLint("NewApi")
	private boolean processKeyEvent(KeyEvent ev) {
		if(mInputConnection == null) return false;
		int key = ev.getKeyCode();

		if (ev.isCtrlPressed()) return false;

		if ((key <= -200 && key > -300) || (key <= -2000 && key > -3000)) {
			int jamo = mHangulEngine.inputCode(key, mHardShift);
			if (jamo != -1) {
				if (mHardShift != 0) {
					jamo = Character.toUpperCase(jamo);
					for(int[] item : SHIFT_CONVERT) {
						if(item[0] == jamo) {
							jamo = item[1];
							break;
						}
					}
				}
				if (mHangulEngine.inputJamo(jamo) == 0) {
					mInputConnection.commitText(String.valueOf((char) jamo), 1);
					resetCharComposition();
				}
			}
			mCharInput = true;
			mInput = true;
			mSpace = false;
			return true;
		}

		if (key >= KeyEvent.KEYCODE_NUMPAD_0 && key <= KeyEvent.KEYCODE_NUMPAD_RIGHT_PAREN) {
			resetCharComposition();
			return false;
		}

		if (ev.isPrintingKey()) {

			int code = ev.getUnicodeChar(mShiftKeyToggle[mHardShift] | mAltKeyToggle[mHardAlt]);
			this.inputChar((char) code, mHardAlt != 0 && mAltDirect);

			if (mHardShift == 1) {
				mShiftPressing = false;
			}
			if (mHardAlt == 1) {
				mAltPressing = false;
			}
			if (!ev.isAltPressed()) {
				if (mHardAlt == 1) {
					mHardAlt = 0;
				}
			}
			if (!ev.isShiftPressed()) {
				if (mHardShift == 1) {
					mHardShift = 0;
				}
			}
			if (!ev.isShiftPressed() && !ev.isShiftPressed()) {
				updateMetaKeyStateDisplay();
				updateNumKeyboardShiftState();
			}

			return true;

		} else if (key == KeyEvent.KEYCODE_SPACE) {
			// 한글 조합을 종료한다
			resetCharComposition();
			mInputConnection.commitText(" ", 1);
			return true;
		} else if (key == KeyEvent.KEYCODE_DEL) {
			if(!mHangulEngine.backspace()) {
				resetCharComposition();
				return false;
			}
			if (mHangulEngine.getComposing().equals(""))
				resetCharComposition();
			return true;
		} else if (key == DefaultSoftKeyboard.KEYCODE_NON_SHIN_DEL) {
			resetCharComposition();
			mInputConnection.deleteSurroundingText(1, 0);
			return true;
		} else if(key == KeyEvent.KEYCODE_ENTER) {
			resetCharComposition();
			mHardShift = 0;
			mHardAlt = 0;
			updateMetaKeyStateDisplay();
			updateNumKeyboardShiftState();
			EditorInfo editorInfo = getCurrentInputEditorInfo();
			switch(editorInfo.imeOptions & EditorInfo.IME_MASK_ACTION) {
			case EditorInfo.IME_ACTION_SEARCH:
			case EditorInfo.IME_ACTION_GO:
				sendDefaultEditorAction(true);
				return true;

			default:
				return false;
			}
		} else {
			resetCharComposition();
		}

		return false;
	}

	private void shinShift() {
		if(mCurrentEngineMode != null && mCurrentEngineMode.jamoset != null) {
			DefaultSoftKeyboard softKeyboard = (DefaultSoftKeyboard) mInputViewManager;
			boolean capsLock = softKeyboard.isCapsLock();
			if(mHardShift == 2) capsLock = true;
			boolean shift = !softKeyboard.mHardKeyboardHidden && mHardShift == 1;
			softKeyboard.updateKeyLabels();
			if(capsLock) {
				softKeyboard.setCapsLock(true);
				softKeyboard.setShiftState(1);
				mHardShift = 2;
				mShiftPressing = true;
			} else if(shift) {
				mHardShift = 1;
			}
		}
		updateMetaKeyStateDisplay();
		updateNumKeyboardShiftState();
	}

	private void flickAction(String flickAction, int keyCode) {
		switch(flickAction) {
		case FLICK_NONE:
			break;

		case FLICK_SHIFT: {
			switch(keyCode) {
			case me.blog.hgl1002.openwnn.DefaultSoftKeyboard.KEYCODE_QWERTY_SHIFT:
				break;

			default:
				this.mHardShift = 1;
				this.inputChar((char) keyCode, false);
				this.mHardShift = 0;
				break;

			}
			break;
		}
		case FLICK_SYMBOL: {
			for(int[] item : mAltLayout) {
				if(item[0] == keyCode) {
					this.inputChar((char) item[1], true);
					break;
				}
			}
			break;
		}
		case FLICK_SYMBOL_SHIFT: {
			for(int[] item : mAltLayout) {
				if(item[0] == keyCode) {
					this.inputChar((char) item[2], true);
					break;
				}
			}
			break;
		}
		}
	}

	public void updateMetaKeyStateDisplay() {
		int mode = 0;
		if (mHardShift == 0 && mHardAlt == 0){
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_OFF_ALT_OFF;
		} else if(mHardShift == 1 && mHardAlt == 0) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_ON_ALT_OFF;
		} else if(mHardShift == 2  && mHardAlt == 0) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_LOCK_ALT_OFF;
		} else if(mHardShift == 0 && mHardAlt == 1) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_OFF_ALT_ON;
		} else if(mHardShift == 0 && mHardAlt == 2) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_OFF_ALT_LOCK;
		} else if(mHardShift == 1 && mHardAlt == 1) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_ON_ALT_ON;
		} else if(mHardShift == 1 && mHardAlt == 2) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_ON_ALT_LOCK;
		} else if(mHardShift == 2 && mHardAlt == 1) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_LOCK_ALT_ON;
		} else if(mHardShift == 2 && mHardAlt == 2) {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_LOCK_ALT_LOCK;
		} else {
			mode = me.blog.hgl1002.openwnn.DefaultSoftKeyboard.HARD_KEYMODE_SHIFT_OFF_ALT_OFF;
		}
		((me.blog.hgl1002.openwnn.DefaultSoftKeyboard) mInputViewManager).updateIndicator(mode);
		mode = DefaultSoftKeyboard.HARD_KEYMODE_LANG
				+ ((me.blog.hgl1002.openwnn.DefaultSoftKeyboard) mInputViewManager).mCurrentLanguage;
		((me.blog.hgl1002.openwnn.DefaultSoftKeyboard) mInputViewManager).updateIndicator(mode);
	}

	private void resetCharComposition() {
		mHangulEngine.resetComposition();
		if(mHangulEngine instanceof TwelveHangulEngine) ((TwelveHangulEngine) mHangulEngine).resetCycle();
	}

	private void updateNumKeyboardShiftState() {
		if(!(mInputViewManager instanceof DefaultSoftKeyboard)) return;
		DefaultSoftKeyboard softKeyboard = (DefaultSoftKeyboard) mInputViewManager;
		if(softKeyboard.mHardKeyboardHidden) return;
		softKeyboard.setShiftState(mHardShift);
		softKeyboard.setCapsLock(mHardShift == 2);
	}

	public void resetHardShift(boolean force) {
		if(mHardShift == 2 && !force) return;
		mHardShift = 0;
		mShiftPressing = false;
	}

	@Override
	public void hideWindow() {
		mInputViewManager.closing();
		super.hideWindow();
		hideStatusIcon();
	}

	@Override
	public void onDestroy() {
		mInputViewManager.closing();
		mSettingsHandler.removeCallbacksAndMessages(null);
		mHardwareKeys.reset();
		if (mTimeOutHandler != null) mTimeOutHandler.removeCallbacksAndMessages(null);
		if (mPreferences != null) mPreferences.unregisterOnSharedPreferenceChangeListener(this);
		EventBus.getDefault().unregister(this);
		if (mSelf.get() == this) mSelf.clear();
		super.onDestroy();
	}

	/** @see android.inputmethodservice.InputMethodService#onComputeInsets */
	@Override public void onComputeInsets(InputMethodService.Insets outInsets) {
		super.onComputeInsets(outInsets);
		outInsets.contentTopInsets = outInsets.visibleTopInsets;
	}

	@TargetApi(Build.VERSION_CODES.R)
    private void updateNavigationBar() {
		// Create a keyboard view to get background color
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		String skin = pref.getString("keyboard_skin", getResources().getString(R.string.keyboard_skin_id_default));
		int id = getResources().getIdentifier("keyboard_" + skin, "layout", getPackageName());
		if(id == 0) id = R.layout.keyboard_white;
		KeyboardView keyboardView = (KeyboardView) getLayoutInflater().inflate(id, null);

		getWindow().getWindow().getDecorView().setOnApplyWindowInsetsListener((view, insets) -> {
			android.graphics.Insets statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars());
			android.graphics.Insets navInsets = insets.getInsets(WindowInsets.Type.navigationBars());
			view.setBackground(keyboardView.getBackground());
			view.setPadding(0, statusBarInsets.top, 0, navInsets.bottom);
			return insets;
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		mInputConnection = getCurrentInputConnection();
		if (mPreparedInputView == null && mInputConnection == null)
			return super.onKeyDown(keyCode, event);
		if (mHardwareKeys.onKeyDown(event)) return true;
		if (mHardwareKeys.isShortcut(event)) return super.onKeyDown(keyCode, event);
		if (mTimeOutHandler == null && !KeyEvent.isModifierKey(keyCode)) {
			mTimeOutHandler = new Handler(Looper.getMainLooper());
			mTimeOutHandler.postDelayed(
					() -> {
						if (mInputConnection != null)
							EventBus.getDefault().post(new InputTimeoutEvent());
						mTimeOutHandler = null;
					},
					mMoachigiDelay);
		}
		InputKeyEvent inputKeyEvent = new InputKeyEvent(event);
		EventBus.getDefault().post(inputKeyEvent);
		if (inputKeyEvent.isCancelled()) {
			mHardwareKeys.consume(event);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean consumed = mHardwareKeys.onKeyUp(event);
		synchronizeHardwareModifiers(event);
		return consumed || super.onKeyUp(keyCode, event);
	}

	@Override
	public void finishComposition() {
		if (mTimeOutHandler != null) {
			mTimeOutHandler.removeCallbacksAndMessages(null);
			mTimeOutHandler = null;
		}
		resetCharComposition();
		if (mInputConnection != null) mInputConnection.finishComposingText();
	}

	@Override
	public void performLanguageAction(String action) {
		onLangKey(action);
	}

	@Override
	public boolean sendKeyEvent(KeyEvent event) {
		InputConnection connection = getCurrentInputConnection();
		return connection != null && connection.sendKeyEvent(event);
	}

	@Override
	public boolean onEvaluateFullscreenMode() {
		return false;
	}

	@Override
	public boolean onEvaluateInputViewShown() {
		super.onEvaluateInputViewShown();
		return !shouldHideKeyboard();
	}

	/** @see android.inputmethodservice.InputMethodService#setCandidatesViewShown */
	@Override public void setCandidatesViewShown(boolean shown) {
		shown = shown && !shouldHideKeyboard();
		super.setCandidatesViewShown(shown);
		if (shown) {
			showWindow(true);
		} else {
			if (mAutoHideMode && mInputViewManager == null) {
				hideWindow();
			}
		}
	}

	@Override
	public void requestHideSelf(int flag) {
		super.requestHideSelf(flag);
		if (mInputViewManager == null) {
			hideWindow();
		}
	}

	public HangulEngine getHangulEngine() {
		return mHangulEngine;
	}

	public int[][] getAltSymbols() {
		return mAltLayout;
	}
}
