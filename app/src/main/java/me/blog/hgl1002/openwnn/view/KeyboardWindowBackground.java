package me.blog.hgl1002.openwnn.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.WindowInsets;

import androidx.annotation.RequiresApi;
import androidx.core.graphics.Insets;

/** Draws behind transparent system navigation without covering the app above the keyboard. */
final class KeyboardWindowBackground extends Drawable {
    private final Paint paint = new Paint();
    private int left;
    private int right;
    private int bottom;

    KeyboardWindowBackground(int color) {
        paint.setColor(color);
    }

    void setColor(int color) {
        if (paint.getColor() != color) {
            paint.setColor(color);
            invalidateSelf();
        }
    }

    @RequiresApi(21)
    void updateInsets(WindowInsets insets) {
        Insets navigation = KeyboardNavigationInsets.read(insets);
        if (left == navigation.left && right == navigation.right && bottom == navigation.bottom) {
            return;
        }
        left = navigation.left;
        right = navigation.right;
        bottom = navigation.bottom;
        invalidateSelf();
    }

    @Override
    public void draw(Canvas canvas) {
        Rect bounds = getBounds();
        canvas.drawRect(bounds.left, bounds.bottom - bottom, bounds.right, bounds.bottom, paint);
        canvas.drawRect(bounds.left, bounds.top, bounds.left + left, bounds.bottom, paint);
        canvas.drawRect(bounds.right - right, bounds.top, bounds.right, bounds.bottom, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(ColorFilter filter) {
        paint.setColorFilter(filter);
        invalidateSelf();
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
