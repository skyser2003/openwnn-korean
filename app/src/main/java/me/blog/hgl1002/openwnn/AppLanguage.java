package me.blog.hgl1002.openwnn;

import android.app.LocaleManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.os.LocaleListCompat;
import androidx.preference.PreferenceManager;

import java.util.Locale;

/** Applies interface language without changing the IME's typing language or process locale. */
public final class AppLanguage {
    public static final String PREFERENCE = "app_language";

    private AppLanguage() {}

    public static String selected(Context context) {
        if (Build.VERSION.SDK_INT >= 33) {
            LocaleManager manager = context.getSystemService(LocaleManager.class);
            LocaleList locales = manager.getApplicationLocales();
            return locales.isEmpty() ? "system" : locales.get(0).getLanguage();
        }
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREFERENCE, "system");
    }

    public static void select(Context context, String language) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREFERENCE, language)
                .apply();
        String tags = "system".equals(language) ? "" : language;
        if (Build.VERSION.SDK_INT >= 33) {
            context.getSystemService(LocaleManager.class)
                    .setApplicationLocales(LocaleList.forLanguageTags(tags));
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(tags));
        }
    }

    public static Context wrap(Context context) {
        String selected = selected(context);
        if ("system".equals(selected)) return context;
        Configuration configuration =
                configuration(context.getResources().getConfiguration(), selected);
        if (Build.VERSION.SDK_INT >= 17) return context.createConfigurationContext(configuration);
        return new LocalizedContext(
                context,
                new Resources(
                        context.getAssets(),
                        context.getResources().getDisplayMetrics(),
                        configuration));
    }

    public static Resources resources(Context context, Resources original) {
        String selected = selected(context);
        if ("system".equals(selected)) return original;
        Configuration localized = configuration(original.getConfiguration(), selected);
        if (Build.VERSION.SDK_INT >= 17)
            return context.createConfigurationContext(localized).getResources();
        return new Resources(original.getAssets(), original.getDisplayMetrics(), localized);
    }

    private static Configuration configuration(Configuration original, String language) {
        Configuration localized = new Configuration(original);
        Locale locale = new Locale(language);
        if (Build.VERSION.SDK_INT >= 24) localized.setLocales(new LocaleList(locale));
        else if (Build.VERSION.SDK_INT >= 17) localized.setLocale(locale);
        else localized.locale = locale;
        return localized;
    }

    private static final class LocalizedContext extends ContextWrapper {
        private final Resources resources;

        private LocalizedContext(Context base, Resources resources) {
            super(base);
            this.resources = resources;
        }

        @Override
        public Resources getResources() {
            return resources;
        }
    }
}
