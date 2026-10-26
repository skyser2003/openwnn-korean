package me.blog.hgl1002.openwnn.view;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.inputmethodservice.KeyboardView;
import android.os.Build;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowMetrics;

import androidx.annotation.RequiresApi;

import me.blog.hgl1002.openwnn.BuildConfig;

import java.util.ArrayDeque;

/** Bounded, local window geometry history. Never reads editor text, key events, or view labels. */
public final class KeyboardLayoutDiagnostics {
    private static final History HISTORY = new History(32);

    private KeyboardLayoutDiagnostics() {}

    @RequiresApi(21)
    static synchronized void record(String event, Window window, View input,
            WindowInsets incoming, WindowInsets remaining, boolean paddingNeedsLayout) {
        try {
            View decor = window.getDecorView();
            WindowInsets root = Build.VERSION.SDK_INT >= 23 ? decor.getRootWindowInsets() : incoming;
            WindowManager.LayoutParams attributes = window.getAttributes();
            StringBuilder state = new StringBuilder();
            state.append("window type=").append(attributes.type)
                    .append(" flags=0x").append(Integer.toHexString(attributes.flags))
                    .append(" softInput=0x").append(Integer.toHexString(attributes.softInputMode))
                    .append(" systemUi=0x").append(Integer.toHexString(decor.getSystemUiVisibility()))
                    .append(" focus=").append(decor.hasWindowFocus())
                    .append(" paddingNeedsLayout=").append(paddingNeedsLayout).append('\n');
            Configuration configuration = decor.getResources().getConfiguration();
            state.append("screenDp=").append(configuration.screenWidthDp).append('x')
                    .append(configuration.screenHeightDp)
                    .append(" orientation=").append(configuration.orientation)
                    .append(" density=").append(decor.getResources().getDisplayMetrics().density)
                    .append(" display=").append(decor.getDisplay() == null ? -1 : decor.getDisplay().getDisplayId())
                    .append('\n');
            state.append("root=").append(describeInsets(root)).append('\n');
            state.append("incoming=").append(describeInsets(incoming)).append('\n');
            state.append("remaining=").append(describeInsets(remaining)).append('\n');
            appendView(state, "decor", decor);
            View view = input;
            for (int level = 0; view != null && view != decor && level < 6; level++) {
                appendView(state, level == 0 ? "input" : "parent" + level, view);
                view = view.getParent() instanceof View ? (View) view.getParent() : null;
            }
            if (input instanceof ViewGroup) {
                ViewGroup group = (ViewGroup) input;
                if (group.getChildCount() > 0) appendView(state, "lastChild", group.getChildAt(group.getChildCount() - 1));
            }
            String snapshot = state.toString();
            if (!HISTORY.isDuplicate(snapshot)) {
                // These fields can require system calls. Read them only for a changed state.
                Rect visibleFrame = new Rect();
                decor.getWindowVisibleDisplayFrame(visibleFrame);
                HISTORY.add(event, snapshot, "visibleFrame=" + visibleFrame.toShortString()
                        + "\n" + describeWindowMetrics(window));
            }
        } catch (RuntimeException failure) {
            // Diagnostics must not interrupt typing if an OEM cannot provide a geometry field.
            HISTORY.add("capture-error", failure.getClass().getSimpleName(), "");
        }
    }

    public static synchronized String report() {
        String states = Build.VERSION.SDK_INT < 21
                ? "Window layout recording requires Android 5.0 (API 21) or later.\n"
                : HISTORY.size() == 0
                        ? "No keyboard window data yet. Open the keyboard and try again.\n"
                        : HISTORY.dump();
        return "OpenWnn keyboard layout report\n"
                + "version=" + BuildConfig.VERSION_NAME + " code=" + BuildConfig.VERSION_CODE + "\n"
                + "device=" + Build.MANUFACTURER + " " + Build.MODEL + "\n"
                + "android=" + Build.VERSION.RELEASE + " sdk=" + Build.VERSION.SDK_INT + "\n"
                + "Geometry only: no editor text, key events, or view labels.\n"
                + "Recent states (oldest first; milliseconds since boot):\n"
                + states;
    }

    @RequiresApi(21)
    private static void appendView(StringBuilder state, String name, View view) {
        int[] position = new int[2];
        view.getLocationInWindow(position);
        int[] screen = new int[2];
        view.getLocationOnScreen(screen);
        state.append(name).append('=').append(view.getClass().getSimpleName())
                .append(" window=[").append(position[0]).append(',').append(position[1]).append(',')
                .append(view.getWidth()).append(',').append(view.getHeight()).append(']')
                .append(" screen=[").append(screen[0]).append(',').append(screen[1]).append(']')
                .append(" padding=").append(rect(view.getPaddingLeft(), view.getPaddingTop(), view.getPaddingRight(), view.getPaddingBottom()))
                .append(" shown=").append(view.isShown())
                .append(" attached=").append(view.isAttachedToWindow())
                .append(" layout=").append(view.isLayoutRequested())
                .append(" fits=").append(view.getFitsSystemWindows());
        ViewGroup.LayoutParams parameters = view.getLayoutParams();
        if (parameters instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams margins = (ViewGroup.MarginLayoutParams) parameters;
            state.append(" margins=").append(rect(margins.leftMargin, margins.topMargin, margins.rightMargin, margins.bottomMargin));
        }
        if (view instanceof KeyboardView && ((KeyboardView) view).getKeyboard() != null) {
            state.append(" keyModelHeight=").append(((KeyboardView) view).getKeyboard().getHeight());
        }
        state.append('\n');
    }

    @RequiresApi(21)
    private static String describeInsets(WindowInsets insets) {
        if (insets == null) return "none";
        if (Build.VERSION.SDK_INT >= 30) {
            return describeType(insets, WindowInsets.Type.navigationBars(), "nav")
                    + " " + describeType(insets, WindowInsets.Type.captionBar(), "caption")
                    + " mandatory=" + rect(insets.getInsets(WindowInsets.Type.mandatorySystemGestures()))
                    + " tappable=" + rect(insets.getInsets(WindowInsets.Type.tappableElement()));
        }
        return "legacy=" + rect(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom())
                + " stable=" + rect(insets.getStableInsetLeft(), insets.getStableInsetTop(),
                insets.getStableInsetRight(), insets.getStableInsetBottom());
    }

    @RequiresApi(30)
    private static String describeType(WindowInsets insets, int type, String name) {
        return name + "=" + rect(insets.getInsets(type))
                + " stable=" + rect(insets.getInsetsIgnoringVisibility(type))
                + " visible=" + insets.isVisible(type);
    }

    @RequiresApi(21)
    private static String describeWindowMetrics(Window window) {
        if (Build.VERSION.SDK_INT < 30) return "";
        try {
            WindowMetrics metrics = window.getWindowManager().getCurrentWindowMetrics();
            return "metricsBounds=" + metrics.getBounds().toShortString()
                    + " metricsInsets=" + describeInsets(metrics.getWindowInsets()) + "\n";
        } catch (RuntimeException unavailable) {
            return "metricsUnavailable=" + unavailable.getClass().getSimpleName() + "\n";
        }
    }

    @RequiresApi(29)
    private static String rect(android.graphics.Insets insets) {
        return rect(insets.left, insets.top, insets.right, insets.bottom);
    }

    private static String rect(int left, int top, int right, int bottom) {
        return "[" + left + "," + top + "," + right + "," + bottom + "]";
    }

    static final class History {
        private final int limit;
        private final ArrayDeque<String> entries = new ArrayDeque<>();
        private String lastState;

        History(int limit) {
            this.limit = limit;
        }

        boolean isDuplicate(String state) {
            return state.equals(lastState);
        }

        void add(String event, String state, String extra) {
            if (isDuplicate(state)) return;
            if (entries.size() == limit) entries.removeFirst();
            entries.addLast("+" + SystemClock.uptimeMillis() + "ms " + event + "\n" + state + "\n" + extra);
            lastState = state;
        }

        int size() {
            return entries.size();
        }

        String dump() {
            StringBuilder report = new StringBuilder();
            for (String entry : entries) report.append(entry).append('\n');
            return report.toString();
        }
    }
}
