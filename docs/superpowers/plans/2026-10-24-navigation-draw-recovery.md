# Navigation redraw recovery implementation plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Repair navigation spacing before drawing when the latest bar state changes without another layout or inset callback.

**Architecture:** Keep the existing window inset and ancestor gap calculation. Observe pre-draw as well as global layout, and track navigation padding awaiting layout so an affected frame waits until key bounds are safe. Synchronize the navigation background using the same latest insets, invalidating it only on geometry changes. Register observers after attachment and remove both together when the controller closes or its view tree changes.

**Tech stack:** Java, Android Views, Robolectric, Gradle.

**Spec:** User's intermittent navigation overlap report and `docs/keyboard-options.md` navigation behavior.

## Global constraints

- Keep minSdk 14; navigation handling starts at API 21.
- Use current navigation/caption insets without a fixed bar height.
- Count spacing already supplied by ancestors once.
- Remove spacing for hidden bars and move it for side navigation.
- Device-specific behavior still needs verification on the reporting device.

## Review focus

- Bar state changes between layout and drawing.
- Repeated draws must settle without perpetual cancellation or accumulated padding.
- Parent-provided spacing must not become a duplicate gap.
- Hidden bars and side navigation must use the latest geometry.
- Closing or reattaching must not leave active callbacks on the old view tree.

## Task 1: Recover navigation padding before drawing

**Files:**
- Modify `app/src/main/java/me/blog/hgl1002/openwnn/view/KeyboardNavigationInsets.java`.
- Modify `app/src/main/java/me/blog/hgl1002/openwnn/view/KeyboardWindowBackground.java` to repaint changed bar geometry without invalidating every draw.
- Test `app/src/test/java/me/blog/hgl1002/openwnn/KeyboardNavigationRecoveryTest.java`.
- Update `docs/keyboard-options.md`.

**Interfaces:** Keep the public constructor, `update(View, int)`, and `close()` unchanged. Add the `ViewTreeObserver.OnPreDrawListener` callback.

- [x] Add a regression which changes root navigation insets after layout, dispatches only pre-draw, and checks restored padding, postponed drawing, and correct key bounds after layout.
- [x] Run `./gradlew :app:testDebugUnitTest --tests '*KeyboardNavigationRecoveryTest' --offline --console=plain` and observe the regression fail.
- [x] Share the existing geometry reconciliation between global layout and pre-draw. Track navigation padding awaiting layout so pre-draw postpones the affected frame even when global layout made the correction. Register and remove both tree observers.
- [x] Cover repeated draws, parent-provided spacing, hidden/side navigation, and listener cleanup through the draw callback.
- [x] Run the focused regression suite, then all unit tests and `:app:assembleDebug`; check the final diff.
- [x] Update device-check documentation to include switching app focus, unlocking, and drawing after late inset changes.
- [x] Review the patch before committing and publishing a release through the existing workflow.

Baseline: all 149 Android tests passed before changes. No Android device is attached to this environment.

Verification: the late-navigation regression failed on API 33/35 before the repair. Review found an unsafe frame after global-layout repair and duplicated observers when constructing before attachment; both were reproduced by failing tests and then corrected. The background raster test uses native graphics, matching the existing theme tests. Final `:app:testDebugUnitTest :app:assembleDebug` passed with 163 Android tests, zero failures; all five Python version tests passed. `git diff --check` passed. The reporting Samsung's actual failure, folding/unlocking behavior, and runtime frame timing still need device confirmation.
