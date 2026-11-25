package me.blog.hgl1002.openwnn;

import android.content.Context;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import me.blog.hgl1002.openwnn.view.KeyboardLayoutDiagnostics;

public class SebeolHangulIMEControlPanel extends AppCompatActivity implements PreferenceFragmentCompat.OnPreferenceStartFragmentCallback {

	@Override
	protected void attachBaseContext(Context base) {
		super.attachBaseContext(AppLanguage.wrap(base));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		if(savedInstanceState == null) {
			getSupportFragmentManager()
					.beginTransaction()
					.replace(R.id.frame_layout, new HeadersFragment())
					.commit();
		}
		getSupportFragmentManager().addOnBackStackChangedListener(() -> {
			getSupportActionBar().setDisplayHomeAsUpEnabled(getSupportFragmentManager().getBackStackEntryCount() != 0);
		});
        View settingsRoot = findViewById(R.id.settings_root);
        ViewCompat.setOnApplyWindowInsetsListener(
                settingsRoot,
                (view, windowInsets) -> {
                    Insets bars = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
                    Insets tappable =
                            windowInsets.getInsets(WindowInsetsCompat.Type.tappableElement());
                    // Some system buttons can still intercept taps when the navigation inset is
                    // empty.
                    view.setPadding(
                            bars.left,
                            bars.top,
                            bars.right,
                            Math.max(bars.bottom, tappable.bottom));
                    return WindowInsetsCompat.CONSUMED;
                });
	}

	@Override
	public boolean onPreferenceStartFragment(@NonNull PreferenceFragmentCompat caller, @NonNull Preference pref) {
		Bundle args = pref.getExtras();
		String fragmentClassName = pref.getFragment();
		Fragment fragment = getSupportFragmentManager().getFragmentFactory().instantiate(getClassLoader(), fragmentClassName);
		fragment.setArguments(args);
		fragment.setTargetFragment(caller, 0);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.frame_layout, fragment)
				.addToBackStack(null)
				.commit();
		setTitle(pref.getTitle());
		return true;
	}

	@Override
	public boolean onSupportNavigateUp() {
		if(getSupportFragmentManager().popBackStackImmediate()) {
			return true;
		}
		return super.onSupportNavigateUp();
	}

	public static class HeadersFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			if (PreferenceManager.getDefaultSharedPreferences(getContext())
					.getBoolean("reveal_dev_settings", false)) {
				setPreferencesFromResource(R.xml.openwnn_pref_ko_headers_dev, rootKey);
			} else {
				setPreferencesFromResource(R.xml.openwnn_pref_ko_headers, rootKey);
			}
		}
	}

	public static class InputMethodFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			setPreferencesFromResource(R.xml.openwnn_pref_ko_method, rootKey);
		}
	}

	public static class KeyboardAppearanceFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			setPreferencesFromResource(R.xml.openwnn_pref_ko_appearance, rootKey);
		}
	}

	public static class SoftKeyboardFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			setPreferencesFromResource(R.xml.openwnn_pref_ko_softkeyboard, rootKey);
		}
	}

	public static class HardKeyboardFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			setPreferencesFromResource(R.xml.openwnn_pref_ko_hardkeyboard, rootKey);
		}
	}

	public static class SystemFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			setPreferencesFromResource(R.xml.openwnn_pref_ko_system, rootKey);
			ListPreference language = findPreference(AppLanguage.PREFERENCE);
			if (language != null) {
				language.setValue(AppLanguage.selected(requireContext()));
				language.setSummaryProvider(ListPreference.SimpleSummaryProvider.getInstance());
				language.setOnPreferenceChangeListener(
						(preference, value) -> {
							AppLanguage.select(requireContext(), (String) value);
							return true;
						});
			}
		}
	}

	public static class AboutFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			setPreferencesFromResource(R.xml.openwnn_pref_ko_about, rootKey);
			Preference copyLayout = findPreference("copy_keyboard_layout_report");
			if (copyLayout != null) {
                copyLayout.setOnPreferenceClickListener(
                        preference -> {
                            ClipboardManager clipboard =
                                    (ClipboardManager)
                                            requireContext()
                                                    .getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setPrimaryClip(
                                    ClipData.newPlainText(
                                            "OpenWnn keyboard layout report",
                                            KeyboardLayoutDiagnostics.report()));
                            Toast.makeText(
                                            requireContext(),
                                            R.string.keyboard_layout_report_copied,
                                            Toast.LENGTH_SHORT)
                                    .show();
                            return true;
                        });
			}
		}
	}

	public static class DeveloperFragment extends PreferenceFragmentCompat {
		@Override
		public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {
			setPreferencesFromResource(R.xml.openwnn_pref_ko_developer, rootKey);
		}
	}

}
