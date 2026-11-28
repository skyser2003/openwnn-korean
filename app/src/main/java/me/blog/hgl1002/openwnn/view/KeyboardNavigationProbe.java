package me.blog.hgl1002.openwnn.view;

import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

/** Measures where WindowManager places a window above visible external navigation. */
@RequiresApi(35)
final class KeyboardNavigationProbe {
    private final Window window;
    private final View decor;
    private final int[] position = new int[2];
    private View probe;
    private boolean attempted;
    private int fittedBottom;

    KeyboardNavigationProbe(Window window) {
        this.window = window;
        decor = window.getDecorView();
    }

    int bottomOverlap() {
        start();
        if (fittedBottom == 0) return 0;
        decor.getLocationOnScreen(position);
        if (fittedBottom <= position[1]) return 0;
        return Math.max(0, position[1] + decor.getHeight() - fittedBottom);
    }

    private void start() {
        // Add it after the real IME has attached, so the touchable window remains the
        // one WindowManager uses to publish IME insets to the editor.
        WindowManager.LayoutParams ime = window.getAttributes();
        if (attempted || ime.token == null || !decor.isAttachedToWindow()) return;
        attempted = true;
        View view = new View(decor.getContext());
        view.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        view.getViewTreeObserver()
                .addOnPreDrawListener(
                        () -> {
                            view.getLocationOnScreen(position);
                            int bottom = position[1] + view.getHeight();
                            if (view == probe && view.isLaidOut() && bottom != fittedBottom) {
                                fittedBottom = bottom;
                                decor.invalidate();
                            }
                            return true;
                        });
        // Like the platform handwriting window, a non-touchable TYPE_INPUT_METHOD
        // window can use the IME token without taking over the keyboard or its input.
        WindowManager.LayoutParams attributes =
                new WindowManager.LayoutParams(
                        WindowManager.LayoutParams.MATCH_PARENT,
                        1,
                        WindowManager.LayoutParams.TYPE_INPUT_METHOD,
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        PixelFormat.TRANSLUCENT);
        attributes.token = ime.token;
        attributes.gravity = Gravity.BOTTOM;
        attributes.alpha = 0f;
        attributes.layoutInDisplayCutoutMode = ime.layoutInDisplayCutoutMode;
        attributes.setTitle("OpenWnn navigation measurement");
        attributes.setFitInsetsTypes(
                WindowInsets.Type.navigationBars() | WindowInsets.Type.tappableElement());
        attributes.setFitInsetsSides(WindowInsets.Side.BOTTOM);
        attributes.setFitInsetsIgnoringVisibility(false);
        try {
            window.getWindowManager().addView(view, attributes);
            probe = view;
        } catch (WindowManager.BadTokenException
                | WindowManager.InvalidDisplayException
                | SecurityException unavailable) {
            // A display or IME token can be replaced before the add reaches WindowManager.
        }
    }

    void stop() {
        View view = probe;
        probe = null;
        fittedBottom = 0;
        attempted = false;
        if (view == null) return;
        try {
            window.getWindowManager().removeViewImmediate(view);
        } catch (IllegalArgumentException alreadyRemoved) {
            // WindowManager can remove all windows when the IME token is destroyed.
        }
    }
}
