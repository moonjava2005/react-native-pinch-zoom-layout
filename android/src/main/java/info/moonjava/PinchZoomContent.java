package info.moonjava;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


class PinchZoomContent extends FrameLayout {
    private OnLayoutListener onLayoutListener;

    public PinchZoomContent(@NonNull Context context) {
        super(context);
    }

    public PinchZoomContent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PinchZoomContent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (this.onLayoutListener != null) {
            this.onLayoutListener.onLayout((right - left), (bottom - top));
        }
    }

    //    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View childView;
        if (getChildCount() > 0) {
            childView = getChildAt(0);
            if (childView != null) {
                int childWidth = childView.getWidth();
                int childHeight = childView.getHeight();
                if (childWidth > 0) {
                    widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
                }
                if (childHeight > 0) {
                    heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setOnLayoutListener(OnLayoutListener onLayoutListener) {
        this.onLayoutListener = onLayoutListener;
    }

    public OnLayoutListener getOnLayoutListener() {
        return onLayoutListener;
    }

    interface OnLayoutListener {
        void onLayout(int width, int height);
    }
}
