# Navigation diagnostics plan

The October 24 update still overlaps system navigation on the reporting device.
Existing synthetic tests do not reproduce the device's actual inset data. The
current spacing algorithm assumes navigation/caption insets match the controls
visible on screen; refreshing that assumption more often has not resolved the
report. Gather real window data before choosing another spacing change.

## Implementation

- [x] Keep a bounded, deduplicated in-memory history of actual root/dispatched/
  remaining bar insets, stable insets and visibility, window flags, display/window
  metrics, input/parent geometry, padding and pending layout state.
- [x] Read geometry only. Exclude text, key events, editor contents and app names.
- [x] Record relevant inset/layout/draw/close events without changing padding,
  consuming different insets, or requesting additional layouts/draws.
- [x] Add About → Copy keyboard layout report in English and Korean. Copy only
  after the user taps it. Keep the recent IME state when settings takes focus.
- [x] Test bounded history, deduplication, raw/stable mismatches, text exclusion,
  and the clipboard action. Run all regression tests and build the APK.
- [x] Review the diagnostic recording and clipboard action.
- [ ] Publish a signed diagnostic release through the existing workflow.

## Device follow-up

After the overlap occurs, open OpenWnn settings → About → Copy keyboard layout
report and paste the report into this conversation. Confirm device model,
Android/One UI version, installed APK version, and whether folding triggers it.
Device confirmation and an actual spacing correction remain pending this data.

Review found no production blockers. Minor compatibility points addressed:
potential system calls for visible-display-frame/window metrics occur only on
changed snapshots, and Android versions below API 21 receive an explicit
recording-support message. Real Samsung retention through a settings transition,
runtime overhead and the overlap's cause still require device evidence.

Verification before publishing: 170 Android tests passed, including the existing
163 tests, report privacy/history cases, API 21 inset capture and clipboard action
on API 21/28/35. `assembleDebug`, all five Python version tests and
`git diff --check` passed.
