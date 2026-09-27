# Keyboard options

Under **System settings**, **App language** selects System default, English, or
한국어. This changes the settings and keyboard interface, independently of the
Korean/English typing mode. Android 13 and later also expose the supported
languages in Android's per-app language settings.

Under **Keyboard appearance**, **Keyboard theme** starts with **Follow system**,
the default when no theme has been selected. It uses White in light mode and Dark
in dark mode, and updates when Android's theme changes. Explicit White, Dark,
Flat dark, and Flat blue choices remain fixed. The keyboard margins, indicator
strip, and bottom navigation area match the selected keyboard theme. Navigation
icons also change contrast; Android versions before 8.0 retain a black navigation
bar because they cannot show dark navigation icons.
The keys sit above Android's navigation controls. Spacing follows the current
navigation/caption bar inset (including gesture mode and landscape side bars),
and disappears when no bar space is needed. Space already reserved by Android
is not added a second time.

Under **Hardware keyboard**:

- **Hide software keyboard completely** hides the keyboard window, number pad,
  and indicator strip while a physical keyboard is available. Composition and
  hardware language switching remain active. Disconnecting the keyboard restores
  the software keyboard for the current text editor.
- **Non-text input layout** defaults to **Follow hardware alphabet layout**.
  Colemak and Dvorak use their alphabet mappings for app controls, including
  single-key and Shift-key commands outside text fields and Ctrl, Alt, and Meta
  shortcuts. **QWERTY key positions** preserves the original QWERTY keys for these
  controls. Both choices apply in English and Korean modes; text entry still
  uses the selected typing layout. Existing shortcut-layout selections are kept.
- **Right Alt types symbols** preserves right-Alt character entry. Left Alt is
  available for application shortcuts. Disable this setting to use right Alt for
  shortcuts too. Ctrl+Alt combinations remain shortcuts.

The hardware language-key combination uses the existing language-key actions in
**System settings**. The short action runs on release. Holding the combination
for the long-press timeout runs the long action once, without also running the
short action or inserting repeated spaces. The default timeout is Android's
long-press timeout. Capturing a combination records its modifiers automatically;
the modifier checkboxes can also be edited before tapping OK.

For an alphabet layout configured inside this app, keep Android's physical
keyboard layout at QWERTY. Applying Colemak or Dvorak in both places would map
ordinary characters twice. The IME uses the active editor information, not the
visibility of its keyboard, to distinguish text entry from app controls. Views
without an active editor and `TYPE_NULL` connections use key events. Custom apps
that report a text editor while showing a canvas cannot always be distinguished
automatically; legacy text fields using `TYPE_NULL` also follow the non-text
layout. An input method can only remap keys Android delivers to it; keys
intercepted by the system or before IME dispatch are outside this setting's control.

## Building and testing

Use JDK 17, Android SDK platform 36, and build-tools 35.0.0. Set `JAVA_HOME` and
`ANDROID_HOME` for the installed tools, and apply any local `.env`/`.envrc` before
running Gradle.

```sh
./gradlew :app:assembleDebug :app:testDebugUnitTest
```

The installable, debug-signed APK is `app/build/outputs/apk/debug/app-debug.apk`.
It uses the original application ID. Updating an installed copy requires the
same signing key; preserve existing settings before replacing a differently
signed installation.

Host tests cover hidden input sessions and editor changes on Android API 28 and
33, interface localization on API 21/28/33, non-text event pairs and repeats,
composition, and language-key capture and short/long presses.
Theme tests cover system changes and explicit choices on API 21/28/33/35,
including navigation contrast, hardware indicators, and the transparent area
above the keyboard.
Touch tests on API 28/33/35 cover reopening the keyboard in the same editor,
single taps and long presses after reopening, and cancellation of pending
touches when the keyboard hides or the input view finishes. Reopening resumes
tap handling without needing a new editor session.
Navigation layout tests on API 21/28/33/35 check key bounds above system buttons,
gesture/hidden navigation, side bars, repeated inset delivery, and spacing
already reserved by the window. API 33/35 also cover the IME caption bar.

Device checks still needed for app-specific behavior:

1. With Colemak selected inside the IME, test Chrome Alt+D using the physical
   QWERTY G key. Confirm that the address bar receives focus and no character is
   inserted. Test both an already-focused text editor and a page without one.
2. Select QWERTY under Non-text input layout and repeat using physical D. Test
   Ctrl shortcuts and switching between Korean and English input. In a drawing
   app, test single-key and Shift-key commands on the canvas under both choices,
   then open a text field and verify that Colemak typing still works. Hold a
   command key while switching focus and check that repeats/releases stay paired.
3. Connect/disconnect the hardware keyboard, rotate the device, and switch text
   fields with complete hiding enabled. Check for leftover keyboard space.
4. Assign Shift+Space, Ctrl+Space, and Alt+Space in turn. Verify short and long
   actions, both modifier-release orders, and holding beyond key repeat.
5. Select Follow system and toggle Android's light/dark mode while the keyboard
   is visible. Repeat with a fixed theme, gesture and three-button navigation,
   landscape orientation, and the hardware-keyboard indicator/number-pad views.
   Check that the bottom area matches and the app above the keyboard remains visible.
   Check that the bottom key row is above the system buttons with no duplicate gap.
6. In Chrome, focus a text field, type, dismiss the keyboard, and tap the same
   field to reopen it. Repeat several times and check that single taps still
   type and long presses perform their configured action only once.

The original revision has 40 Android lint errors (missing Korean translations
and an indentation issue in the composition engine). These are separate from
the build and regression-test checks.
