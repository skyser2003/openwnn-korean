package me.blog.hgl1002.openwnn.view;

import android.os.Build;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowInsets;

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
    private final KeyboardWindowBackground surface;
    private final Runnable refreshAfterAttach = this::refresh;
    private final int[] decorPosition = new int[2];
    private final int[] inputPosition = new int[2];
    private View inputView;
    private WindowInsets lastInsets;
    private ViewTreeObserver observedTree;
    private boolean paddingNeedsLayout;
    private boolean closed;

    public KeyboardNavigationInsets(Window window, int background) {
        decor = window.getDecorView();
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

    private void refresh() {
        if (closed) return;
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
        lastInsets = insets;
        surface.updateInsets(insets);
        WindowInsets remaining = view.onApplyWindowInsets(insets);
        if (inputView == null) return remaining;
        Insets navigation = read(remaining);
        applyPadding(navigation);
        if (navigation.equals(Insets.NONE)) return remaining;
        if (Build.VERSION.SDK_INT >= 29) {
            return remaining.inset(navigation.left, 0, navigation.right, navigation.bottom);
        }
        return remaining.replaceSystemWindowInsets(0, remaining.getSystemWindowInsetTop(), 0, 0);
    }

    @Override
    public void onGlobalLayout() {
        reconcilePadding();
    }

    @Override
    public boolean onPreDraw() {
        // Global layout need not run on a draw-only traversal. If newer window state needs
        // more space, let the requested layout finish before displaying the affected keys.
        reconcilePadding();
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
        paddingNeedsLayout = false;
        WindowInsets insets = currentInsets();
        if (insets == null) return false;
        surface.updateInsets(insets);
        Insets navigation = read(insets);
        decor.getLocationInWindow(decorPosition);
        inputView.getLocationInWindow(inputPosition);
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
    }

    @Override
    public void onViewDetachedFromWindow(View view) {
        decor.removeCallbacks(refreshAfterAttach);
        if (view == decor) stopObservingLayout();
    }

    public void close() {
        closed = true;
        decor.removeCallbacks(refreshAfterAttach);
        decor.setOnApplyWindowInsetsListener(null);
        decor.removeOnAttachStateChangeListener(this);
        if (inputView != null) inputView.removeOnAttachStateChangeListener(this);
        stopObservingLayout();
        inputView = null;
        lastInsets = null;
    }

    static Insets read(WindowInsets insets) {
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
