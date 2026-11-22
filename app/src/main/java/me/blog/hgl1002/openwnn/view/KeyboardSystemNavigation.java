package me.blog.hgl1002.openwnn.view;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;

/** Navigation protection independent of a temporarily missing IME inset source. */
@RequiresApi(21)
final class KeyboardSystemNavigation {
    private final View decor;
    private final int[] position = new int[2];
    private final int[] origin = new int[2];

    KeyboardSystemNavigation(View decor) {
        this.decor = decor;
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
}
