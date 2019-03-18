package info.moonjava;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

class PinchZoomContent extends FrameLayout {
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
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = View.MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = View.MeasureSpec.getMode(heightMeasureSpec);
        View childView;
        if (getChildCount() > 0) {
            childView = getChildAt(0);
            if (childView != null) {
                int childWidth = childView.getWidth();
                int childHeight = childView.getHeight();
                if (childWidth > 0) {
                    if (widthMode == View.MeasureSpec.UNSPECIFIED) {
                        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);
                    }
                }
                if (childHeight > 0) {
                    if (heightMode == View.MeasureSpec.UNSPECIFIED) {
                        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
                    }
                }
            }
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
