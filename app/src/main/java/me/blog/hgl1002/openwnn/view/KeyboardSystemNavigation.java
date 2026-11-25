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

    View findFrame(View view, View input) {
        if (view == input) return null;
        if (isNavigationView(view)) return view;
        if (view instanceof ViewGroup) {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i++) {
                View frame = findFrame(group.getChildAt(i), input);
                if (frame != null) return frame;
            }
        }
        return null;
    }

    Insets readRendered(View frame) {
        if (frame == null || !frame.isShown()) return Insets.NONE;
        decor.getLocationInWindow(origin);
        frame.getLocationInWindow(position);
        int left = position[0] - origin[0];
        int top = position[1] - origin[1];
        int right = left + frame.getWidth();
        int bottom = top + frame.getHeight();
        // Ignore unmeasured/full-window containers and hidden backgrounds.
        if (frame.getWidth() >= decor.getWidth() / 2
                && frame.getHeight() > 0
                && frame.getHeight() <= decor.getHeight() / 2
                && bottom == decor.getHeight()) {
            return Insets.of(0, 0, 0, decor.getHeight() - top);
        }
        if (frame.getHeight() >= decor.getHeight() / 2
                && frame.getWidth() > 0
                && frame.getWidth() <= decor.getWidth() / 2) {
            if (left == 0) return Insets.of(right, 0, 0, 0);
            if (right == decor.getWidth()) return Insets.of(0, 0, decor.getWidth() - left, 0);
        }
        return Insets.NONE;
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
