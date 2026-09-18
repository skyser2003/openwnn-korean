package me.blog.hgl1002.openwnn.view;

import android.os.Build;
import android.view.View;
import android.view.WindowInsets;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;

/** Reserves only the navigation space that the window has not already handled. */
@RequiresApi(21)
final class KeyboardNavigationInsets {
    private KeyboardNavigationInsets() {}

    static Insets read(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= 30) {
            android.graphics.Insets navigation =
                    insets.getInsets(
                            WindowInsets.Type.navigationBars() | WindowInsets.Type.captionBar());
            return Insets.of(navigation.left, 0, navigation.right, navigation.bottom);
        }
        return Insets.of(
                insets.getSystemWindowInsetLeft(),
                0,
                insets.getSystemWindowInsetRight(),
                insets.getSystemWindowInsetBottom());
    }

    static WindowInsets apply(View inputView, WindowInsets remaining) {
        Insets navigation = read(remaining);
        // This container owns the system-bar spacing. User-configured keyboard margins are
        // inside its children. Assign absolute values so redispatch and reopening cannot add up.
        inputView.setPadding(
                navigation.left, inputView.getPaddingTop(), navigation.right, navigation.bottom);
        if (navigation.equals(Insets.NONE)) return remaining;
        if (Build.VERSION.SDK_INT >= 29) {
            return remaining.inset(navigation.left, 0, navigation.right, navigation.bottom);
        }
        return remaining.replaceSystemWindowInsets(0, remaining.getSystemWindowInsetTop(), 0, 0);
    }
}
