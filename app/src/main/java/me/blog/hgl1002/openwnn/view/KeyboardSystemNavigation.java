package me.blog.hgl1002.openwnn.view;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;

/** Navigation protection independent of a temporarily missing IME inset source. */
@RequiresApi(21)
final class KeyboardSystemNavigation {
    private final View decor;
    private final int[] position = new int[2];
    private final int[] origin = new int[2];
    private boolean modeDirty = true;
    private boolean samsungButtons;

    KeyboardSystemNavigation(View decor) {
        this.decor = decor;
    }

    void invalidate() {
        modeDirty = true;
    }

    boolean hasSamsungButtons() {
        if (!modeDirty) return samsungButtons;
        modeDirty = false;
        samsungButtons = false;
        if (Build.VERSION.SDK_INT < 30 || !"samsung".equalsIgnoreCase(Build.MANUFACTURER)) {
            return false;
        }
        try {
            // One UI exposes its own mode; navigation_mode can still say "buttons"
            // while Samsung gestures are enabled, so prefer the One UI setting.
            int gestures = Settings.Global.getInt(decor.getContext().getContentResolver(),
                    "navigation_bar_gesture_while_hidden", -1);
            if (gestures != -1) {
                samsungButtons = gestures == 0;
            } else {
                int mode = Settings.Secure.getInt(decor.getContext().getContentResolver(),
                        "navigation_mode", -1);
                int id = decor.getResources().getIdentifier(
                        "config_navBarInteractionMode", "integer", "android");
                if (mode == -1 && id != 0) mode = decor.getResources().getInteger(id);
                samsungButtons = mode == 0;
            }
        } catch (SecurityException unavailable) {
            // Rendered navigation bounds and normal insets still protect other devices.
        }
        return samsungButtons;
    }

    Insets readRendered(ViewGroup root, View input) {
        decor.getLocationInWindow(origin);
        return readRenderedView(root, input);
    }

    private Insets readRenderedView(View view, View input) {
        if (view == input || !view.isShown()) return Insets.NONE;
        if (isNavigationView(view)) {
            view.getLocationInWindow(position);
            int left = position[0] - origin[0];
            int top = position[1] - origin[1];
            int right = left + view.getWidth();
            int bottom = top + view.getHeight();
            // Measure actual system-owned siblings, including the IME's navigation
            // frame. Ignore unmeasured/full-window containers and hidden backgrounds.
            if (view.getWidth() >= decor.getWidth() / 2
                    && view.getHeight() > 0 && view.getHeight() <= decor.getHeight() / 2
                    && bottom == decor.getHeight()) {
                return Insets.of(0, 0, 0, decor.getHeight() - top);
            }
            if (view.getHeight() >= decor.getHeight() / 2
                    && view.getWidth() > 0 && view.getWidth() <= decor.getWidth() / 2) {
                if (left == 0) return Insets.of(right, 0, 0, 0);
                if (right == decor.getWidth()) return Insets.of(0, 0, decor.getWidth() - left, 0);
            }
            return Insets.NONE;
        }
        Insets navigation = Insets.NONE;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                navigation = Insets.max(navigation, readRenderedView(group.getChildAt(i), input));
            }
        }
        return navigation;
    }

    private static boolean isNavigationView(View view) {
        for (Class<?> type = view.getClass(); type != null; type = type.getSuperclass()) {
            if ("android.inputmethodservice.navigationbar.NavigationBarFrame".equals(type.getName())) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(30)
    Insets readSamsungButtons(WindowInsets rootInsets, Insets navigation, Rect windowBounds) {
        if ((decor.getSystemUiVisibility() & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) != 0) {
            return Insets.NONE;
        }
        android.graphics.Insets stable = rootInsets == null ? android.graphics.Insets.NONE
                : rootInsets.getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars());
        Insets configured;
        if (stable.left > 0 || stable.right > 0) {
            configured = Insets.of(stable.left, 0, stable.right, 0);
        } else if (navigation.left > 0 || navigation.right > 0) {
            return Insets.NONE;
        } else {
            Resources resources = decor.getResources();
            boolean landscape = resources.getConfiguration().orientation
                    == Configuration.ORIENTATION_LANDSCAPE;
            int height = dimension(landscape ? "navigation_bar_height_landscape"
                    : "navigation_bar_height");
            if (height == 0) height = dimension("navigation_bar_height");
            int canMove = resources.getIdentifier("config_navBarCanMove", "bool", "android");
            boolean side = landscape && resources.getConfiguration().smallestScreenWidthDp < 600
                    && canMove != 0 && resources.getBoolean(canMove);
            if (side) {
                int width = dimension("navigation_bar_width");
                if (width == 0) width = height;
                boolean left = decor.getDisplay() != null
                        && decor.getDisplay().getRotation() == Surface.ROTATION_270;
                configured = Insets.of(left ? width : 0, 0, left ? 0 : width, 0);
            } else {
                // Button navigation stays present while typing on One UI even when the
                // IME's navigation source incorrectly reports hidden/empty after folding.
                configured = Insets.of(0, 0, 0, Math.max(height, stable.bottom));
            }
        }
        Rect bounds = windowBounds;
        if (bounds.isEmpty()) {
            if (decor.getDisplay() == null) return configured;
            DisplayMetrics display = new DisplayMetrics();
            decor.getDisplay().getRealMetrics(display);
            bounds = new Rect(0, 0, display.widthPixels, display.heightPixels);
        }
        decor.getLocationOnScreen(position);
        // The WM may already have placed this entire IME window above/outside the bar.
        return Insets.of(
                configured.left == 0 ? 0
                        : Math.max(0, bounds.left + configured.left - position[0]),
                0,
                configured.right == 0 ? 0
                        : Math.max(0, position[0] + decor.getWidth() - bounds.right + configured.right),
                configured.bottom == 0 ? 0
                        : Math.max(0, position[1] + decor.getHeight() - bounds.bottom + configured.bottom));
    }

    private int dimension(String name) {
        Resources resources = decor.getResources();
        int id = resources.getIdentifier(name, "dimen", "android");
        return id == 0 ? 0 : resources.getDimensionPixelSize(id);
    }
}
