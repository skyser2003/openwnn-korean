package me.blog.hgl1002.openwnn.view;

import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowMetrics;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;

/** Keeps the input view above navigation across inset dispatch, attachment, and drawing. */
@RequiresApi(21)
public final class KeyboardNavigationInsets
        implements View.OnApplyWindowInsetsListener,
                View.OnAttachStateChangeListener,
                ViewTreeObserver.OnGlobalLayoutListener,
                ViewTreeObserver.OnPreDrawListener {
    private final View decor;
    private final Window window;
    private final KeyboardWindowBackground surface;
    private final Runnable refreshAfterAttach = this::refresh;
    private final int[] decorPosition = new int[2];
    private final int[] inputPosition = new int[2];
    private final int[] screenPosition = new int[2];
    private final Rect metricsBounds = new Rect();
    private final Rect visibleDisplayFrame = new Rect();
    private Insets metricsNavigation = Insets.NONE;
    private WindowInsets metricsRootInsets;
    private boolean metricsNeedRefresh = true;
    private final KeyboardSystemNavigation systemNavigation;
    private final KeyboardNavigationProbe navigationProbe;
    private View inputView;
    private WindowInsets lastInsets;
    private WindowInsets lastRemainingInsets;
    private ViewTreeObserver observedTree;
    private boolean paddingNeedsLayout;
    private boolean windowVisible;
    private boolean closed;

    public KeyboardNavigationInsets(Window window, int background) {
        this.window = window;
        decor = window.getDecorView();
        systemNavigation = new KeyboardSystemNavigation(decor);
        navigationProbe =
                Build.VERSION.SDK_INT >= 35 && "samsung".equalsIgnoreCase(Build.MANUFACTURER)
                        ? new KeyboardNavigationProbe(window)
                        : null;
        // Keep the framework's bottom-anchored IME window. Android reports its height
        // to edge-to-edge apps as a bottom inset, so moving the whole window up would
        // leave apps underneath the keyboard by the height of the navigation bar.
        surface = new KeyboardWindowBackground(background);
        decor.setBackground(surface);
        decor.setOnApplyWindowInsetsListener(this);
        decor.addOnAttachStateChangeListener(this);
        observeLayout();
    }

    public void update(View input, int background) {
        if (inputView != input) {
            if (inputView != null) inputView.removeOnAttachStateChangeListener(this);
            inputView = input;
            paddingNeedsLayout = false;
            if (inputView != null) inputView.addOnAttachStateChangeListener(this);
        }
        surface.setColor(background);
        // Reuse the current insets immediately; requesting a future callback alone leaves a
        // reopened or replaced input view unprotected until that callback arrives.
        refresh();
        scheduleRefresh();
    }

    public void setWindowVisible(boolean visible) {
        windowVisible = visible;
        if (!visible) stopProbe();
    }

    private void refresh() {
        if (closed) return;
        metricsNeedRefresh = true;
        WindowInsets insets = currentInsets();
        if (insets != null) decor.dispatchApplyWindowInsets(insets);
        decor.requestApplyInsets();
    }

    private WindowInsets currentInsets() {
        if (Build.VERSION.SDK_INT >= 23) {
            WindowInsets rootInsets = decor.getRootWindowInsets();
            if (rootInsets != null) return rootInsets;
        }
        return lastInsets;
    }

    private void scheduleRefresh() {
        decor.removeCallbacks(refreshAfterAttach);
        // A detached view has no ViewRoot to service an inset request. Its attach callback
        // schedules this again, once the hierarchy is ready.
        if (decor.isAttachedToWindow()) decor.post(refreshAfterAttach);
    }

    @Override
    public WindowInsets onApplyWindowInsets(View view, WindowInsets insets) {
        metricsNeedRefresh = true;
        lastInsets = insets;
        surface.updateInsets(read(insets));
        WindowInsets remaining = view.onApplyWindowInsets(insets);
        lastRemainingInsets = remaining;
        recordLayout("insets-before-padding");
        if (inputView == null) return remaining;
        Insets bars = readBars(remaining);
        Insets navigation = withBottom(bars, readBottomControls(remaining));
        if (Build.VERSION.SDK_INT >= 30 && decor.isLaidOut() && navigation.bottom > 0) {
            navigation = clipBottomToWindow(navigation, readWindowNavigation(insets), remaining);
        }
        applyPadding(navigation);
        // Keep the separate controls geometry available to the framework navigation view.
        if (bars.equals(Insets.NONE)) return remaining;
        if (Build.VERSION.SDK_INT >= 29) {
            return remaining.inset(bars.left, 0, bars.right, bars.bottom);
        }
        return remaining.replaceSystemWindowInsets(0, remaining.getSystemWindowInsetTop(), 0, 0);
    }

    @Override
    public void onGlobalLayout() {
        metricsNeedRefresh = true;
        reconcilePadding();
        recordLayout("layout");
    }

    @Override
    public boolean onPreDraw() {
        // Global layout need not run on a draw-only traversal. If newer window state needs
        // more space, let the requested layout finish before displaying the affected keys.
        reconcilePadding();
        recordLayout("draw");
        return inputView == null
                || !inputView.isAttachedToWindow()
                || !inputView.isShown()
                || !paddingNeedsLayout
                || !inputView.isLayoutRequested();
    }

    private boolean reconcilePadding() {
        if (closed
                || inputView == null
                || !inputView.isAttachedToWindow()
                || !inputView.isShown()
                || inputView.isLayoutRequested()
                || decor.getWidth() == 0
                || decor.getHeight() == 0
                || inputView.getWidth() == 0
                || inputView.getHeight() == 0) return false;
        View navigationFrame = systemNavigation.findFrame(decor, inputView);
        paddingNeedsLayout = false;
        WindowInsets insets = currentInsets();
        Insets navigation = insets == null ? Insets.NONE : read(insets);
        if (Build.VERSION.SDK_INT >= 30) {
            Insets overlap = readWindowNavigation(insets);
            navigation = clipBottomToWindow(Insets.max(navigation, overlap), overlap, insets);
        }
        decor.getLocationInWindow(decorPosition);
        inputView.getLocationInWindow(inputPosition);
        navigation = Insets.max(navigation, systemNavigation.readRendered(navigationFrame));
        if (Build.VERSION.SDK_INT >= 35 && navigationProbe != null && windowVisible) {
            navigation = withBottom(navigation, navigationProbe.bottomOverlap());
        }
        surface.updateInsets(navigation);
        // Verify the space actually reserved by ancestors, rather than assuming that a
        // consumed inset still corresponds to a margin after a window transition.
        int leftGap = Math.max(0, inputPosition[0] - decorPosition[0]);
        int rightGap =
                Math.max(
                        0,
                        decorPosition[0]
                                + decor.getWidth()
                                - inputPosition[0]
                                - inputView.getWidth());
        int bottomGap =
                Math.max(
                        0,
                        decorPosition[1]
                                + decor.getHeight()
                                - inputPosition[1]
                                - inputView.getHeight());
        return applyPadding(
                Insets.of(
                        Math.max(0, navigation.left - leftGap),
                        0,
                        Math.max(0, navigation.right - rightGap),
                        Math.max(0, navigation.bottom - bottomGap)));
    }

    @RequiresApi(30)
    private Insets readWindowNavigation(WindowInsets rootInsets) {
        // A display transition can leave the IME ViewRoot with empty bar sources even
        // after reopening. Window metrics query the current window-manager state instead.
        // Cache between layouts/draws; never carry a snapshot across an inset or show event.
        if (metricsNeedRefresh || metricsRootInsets != rootInsets) {
            metricsNeedRefresh = false;
            metricsRootInsets = rootInsets;
            metricsBounds.setEmpty();
            metricsNavigation = Insets.NONE;
            try {
                WindowMetrics metrics = window.getWindowManager().getCurrentWindowMetrics();
                metricsBounds.set(metrics.getBounds());
                WindowInsets metricsInsets = metrics.getWindowInsets();
                android.graphics.Insets bars =
                        metricsInsets.getInsets(WindowInsets.Type.navigationBars());
                metricsNavigation =
                        withBottom(
                                Insets.of(bars.left, 0, bars.right, bars.bottom),
                                readBottomControls(metricsInsets));
            } catch (RuntimeException unavailable) {
                // Some window contexts cannot supply metrics while switching displays.
                // Keep using delivered insets and retry after the next layout/show event.
            }
        }
        if (metricsBounds.isEmpty()) return Insets.NONE;
        decor.getLocationOnScreen(screenPosition);
        int left = screenPosition[0];
        int top = screenPosition[1];
        int right = left + decor.getWidth();
        int bottom = top + decor.getHeight();
        if (right <= metricsBounds.left
                || left >= metricsBounds.right
                || bottom <= metricsBounds.top
                || top >= metricsBounds.bottom) return Insets.NONE;
        // Metrics describe the display. Only reserve the portion overlapping the IME
        // window, and rule out bounds that still describe the previous display state.
        int bottomOverlap = 0;
        if (metricsBounds.contains(left, top, right, bottom)) {
            if (metricsNavigation.bottom > 0) {
                bottomOverlap =
                        Math.max(0, bottom - metricsBounds.bottom + metricsNavigation.bottom);
            } else if ("samsung".equalsIgnoreCase(Build.MANUFACTURER)) {
                // One UI can lose the typed bar sources during a fold while still
                // reporting the bottom edge of the area available to the IME.
                decor.getWindowVisibleDisplayFrame(visibleDisplayFrame);
                if (visibleDisplayFrame.left == metricsBounds.left
                        && visibleDisplayFrame.right == metricsBounds.right
                        && visibleDisplayFrame.top <= top
                        && visibleDisplayFrame.bottom > top) {
                    bottomOverlap = Math.max(0, bottom - visibleDisplayFrame.bottom);
                }
            }
        }
        return Insets.of(
                metricsNavigation.left == 0
                        ? 0
                        : Math.min(
                                decor.getWidth(),
                                Math.max(0, metricsBounds.left + metricsNavigation.left - left)),
                0,
                metricsNavigation.right == 0
                        ? 0
                        : Math.min(
                                decor.getWidth(),
                                Math.max(0, right - metricsBounds.right + metricsNavigation.right)),
                Math.min(decor.getHeight(), bottomOverlap));
    }

    @RequiresApi(30)
    private Insets clipBottomToWindow(Insets navigation, Insets overlap, WindowInsets rootInsets) {
        // A relocated window can temporarily receive insets calculated for its old frame.
        // When the metrics still describe a visible bar on this display, count only the
        // part overlapping the window. In-window caption space remains independent.
        int left = screenPosition[0];
        int top = screenPosition[1];
        if (navigation.bottom > metricsNavigation.bottom
                || overlap.bottom >= navigation.bottom
                || !metricsBounds.contains(
                        left, top, left + decor.getWidth(), top + decor.getHeight())) {
            return navigation;
        }
        int caption =
                rootInsets == null
                        ? 0
                        : readVisibleType(rootInsets, WindowInsets.Type.captionBar()).bottom;
        return Insets.of(navigation.left, 0, navigation.right, Math.max(overlap.bottom, caption));
    }

    private boolean applyPadding(Insets navigation) {
        if (inputView.getPaddingLeft() != navigation.left
                || inputView.getPaddingRight() != navigation.right
                || inputView.getPaddingBottom() != navigation.bottom) {
            inputView.setPadding(
                    navigation.left,
                    inputView.getPaddingTop(),
                    navigation.right,
                    navigation.bottom);
            paddingNeedsLayout = true;
            return true;
        }
        return false;
    }

    private void observeLayout() {
        // Android transfers a detached view's floating observer into the live tree before
        // attach callbacks. Register only once attached so that transfer cannot duplicate us.
        if (!decor.isAttachedToWindow()) return;
        ViewTreeObserver tree = decor.getViewTreeObserver();
        if (tree == observedTree) return;
        stopObservingLayout();
        observedTree = tree;
        observedTree.addOnGlobalLayoutListener(this);
        observedTree.addOnPreDrawListener(this);
    }

    private void stopObservingLayout() {
        if (observedTree != null && observedTree.isAlive()) {
            observedTree.removeOnGlobalLayoutListener(this);
            observedTree.removeOnPreDrawListener(this);
        }
        observedTree = null;
    }

    @Override
    public void onViewAttachedToWindow(View view) {
        observeLayout();
        scheduleRefresh();
        decor.requestApplyInsets();
        recordLayout("attach");
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        recordLayout("detach");
        decor.removeCallbacks(refreshAfterAttach);
        if (view == decor) {
            stopProbe();
            stopObservingLayout();
        }
    }

    public void close() {
        recordLayout("close");
        closed = true;
        decor.removeCallbacks(refreshAfterAttach);
        decor.setOnApplyWindowInsetsListener(null);
        decor.removeOnAttachStateChangeListener(this);
        if (inputView != null) inputView.removeOnAttachStateChangeListener(this);
        stopProbe();
        stopObservingLayout();
        inputView = null;
        lastInsets = null;
        lastRemainingInsets = null;
    }

    private void stopProbe() {
        if (Build.VERSION.SDK_INT >= 35 && navigationProbe != null) navigationProbe.stop();
    }

    private void recordLayout(String event) {
        KeyboardLayoutDiagnostics.record(event, window, inputView, lastInsets,
                lastRemainingInsets, paddingNeedsLayout);
    }

    private static Insets read(WindowInsets insets) {
        return withBottom(readBars(insets), readBottomControls(insets));
    }

    private static Insets withBottom(Insets insets, int bottom) {
        if (bottom <= insets.bottom) return insets;
        return Insets.of(insets.left, 0, insets.right, bottom);
    }

    private static Insets readBars(WindowInsets insets) {
        if (Build.VERSION.SDK_INT >= 30) {
            return Insets.max(
                    readVisibleType(insets, WindowInsets.Type.navigationBars()),
                    readVisibleType(insets, WindowInsets.Type.captionBar()));
        }
        return Insets.of(
                insets.getSystemWindowInsetLeft(),
                0,
                insets.getSystemWindowInsetRight(),
                insets.getSystemWindowInsetBottom());
    }

    private static int readBottomControls(WindowInsets insets) {
        // SystemUI can report a touch-blocking button strip even when the navigation
        // bar inset is empty. Gesture-only and hidden regions do not supply this inset.
        int bottom = 0;
        if (Build.VERSION.SDK_INT >= 30) {
            bottom = insets.getInsets(WindowInsets.Type.tappableElement()).bottom;
        } else if (Build.VERSION.SDK_INT == 29) {
            bottom = insets.getTappableElementInsets().bottom;
        }
        if (Build.VERSION.SDK_INT >= 35) {
            bottom = Math.max(bottom, readBottomControlBounds(insets));
        }
        return bottom;
    }

    @RequiresApi(35)
    private static int readBottomControlBounds(WindowInsets insets) {
        int height = insets.getFrame().getHeight();
        int types = WindowInsets.Type.navigationBars() | WindowInsets.Type.tappableElement();
        int bottom = 0;
        // A partial-width bar may be represented by rectangles without an edge inset.
        for (Rect bounds : insets.getBoundingRects(types)) {
            if (!bounds.isEmpty() && bounds.bottom == height && bounds.top > 0) {
                bottom = Math.max(bottom, bounds.height());
            }
        }
        return bottom;
    }

    @RequiresApi(30)
    private static Insets readVisibleType(WindowInsets insets, int type) {
        android.graphics.Insets navigation = insets.getInsets(type);
        if (navigation.equals(android.graphics.Insets.NONE) && insets.isVisible(type)) {
            // Recover a visible bar whose current geometry is temporarily empty. Do not reserve
            // stable space for a genuinely hidden bar (for example in immersive mode).
            navigation = insets.getInsetsIgnoringVisibility(type);
        }
        return Insets.of(navigation.left, 0, navigation.right, navigation.bottom);
    }
}
