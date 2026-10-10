package me.blog.hgl1002.openwnn.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.view.WindowInsetsControllerCompat;
import androidx.preference.PreferenceManager;

import me.blog.hgl1002.openwnn.R;

/** Resolves the keyboard skin and its surrounding surfaces from one preference. */
public enum KeyboardTheme {
    WHITE("white", R.layout.keyboard_white, R.color.keyboard_background_white, true),
    DARK("dark", R.layout.keyboard_dark, R.color.keyboard_background_dark, false),
    FLAT_DARK(
            "flat_dark", R.layout.keyboard_flat_dark, R.color.keyboard_background_flat_dark, false),
    FLAT_BLUE(
            "flat_blue",
            R.layout.keyboard_flat_blue,
            R.color.keyboard_background_blue_bottom,
            false);

    private final String skinId;
    private final int layoutResource;
    private final int backgroundResource;
    private final boolean light;

    KeyboardTheme(String skinId, int layoutResource, int backgroundResource, boolean light) {
        this.skinId = skinId;
        this.layoutResource = layoutResource;
        this.backgroundResource = backgroundResource;
        this.light = light;
    }

    public static KeyboardTheme resolve(Context context) {
        String selected =
                PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(
                                "keyboard_skin",
                                context.getString(R.string.keyboard_skin_id_default));
        for (KeyboardTheme theme : values()) {
            if (theme.skinId.equals(selected)) return theme;
        }
        boolean night =
                (context.getResources().getConfiguration().uiMode
                                & Configuration.UI_MODE_NIGHT_MASK)
                        == Configuration.UI_MODE_NIGHT_YES;
        return night ? DARK : WHITE;
    }

    public String getSkinId() {
        return skinId;
    }

    public int getLayoutResource() {
        return layoutResource;
    }

    public int getBackgroundColor(Context context) {
        return context.getResources().getColor(backgroundResource);
    }

    public int getTextColor() {
        return light ? Color.BLACK : Color.WHITE;
    }

    public void applyToWindow(Context context, Window window) {
        if (Build.VERSION.SDK_INT < 21) return;
        int background = getBackgroundColor(context);
        View decor = window.getDecorView();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        // Before API 26, navigation icons are always white and need a dark background.
        window.setNavigationBarColor(
                light && Build.VERSION.SDK_INT < 26 ? Color.BLACK : background);
        if (Build.VERSION.SDK_INT >= 28) window.setNavigationBarDividerColor(background);
        if (Build.VERSION.SDK_INT >= 29) window.setNavigationBarContrastEnforced(false);
        new WindowInsetsControllerCompat(window, decor).setAppearanceLightNavigationBars(light);
    }
}
