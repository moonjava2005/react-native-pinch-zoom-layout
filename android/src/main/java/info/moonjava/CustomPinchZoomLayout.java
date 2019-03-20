package info.moonjava;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.shopgun.android.zoomlayout.ZoomLayout;
import com.shopgun.android.zoomlayout.ZoomOnDoubleTapListener;

public class CustomPinchZoomLayout extends ZoomLayout implements ZoomLayout.OnZoomListener, PinchZoomContent.OnLayoutListener {
    float containerWidth, containerHeight, contentWidth, contentHeight, currentScale = 1;
    private OnCustomZoomListener onCustomZoomListener;
    private ZoomOnDoubleTapListener zoomOnDoubleTapListener;

    public CustomPinchZoomLayout(Context context) {
        super(context);
        this.init(context);
    }

    public CustomPinchZoomLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public CustomPinchZoomLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    void init(Context context) {
        zoomOnDoubleTapListener = new ZoomOnDoubleTapListener(false);
        addOnZoomListener(this);
    }

    @Override
    public void setMinScale(float minScale) {
        this.currentScale = minScale;
        super.setMinScale(minScale);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.containerWidth = (right - left);
        this.containerHeight = (bottom - top);
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean isConsumed = false;
        final int pointerCount = ev.getPointerCount();
        if (pointerCount > 1 || (this.currentScale > getMinScale())) {
            isConsumed = super.onTouchEvent(ev);
        }
        return isConsumed;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int pointerCount = ev.getPointerCount();
        boolean isIntercepted = false;
        if (pointerCount > 1 || (this.currentScale > getMinScale())) {
            isIntercepted = super.onInterceptTouchEvent(ev);
        }
        return isIntercepted;
    }

    @Override
    public void onZoomBegin(ZoomLayout view, float scale) {

    }

    @Override
    public void onZoom(ZoomLayout view, float scale) {
        this.currentScale = scale;
        if (this.onCustomZoomListener != null) {
            this.onCustomZoomListener.onZoom(this.currentScale, this.containerWidth, this.containerHeight, this.contentWidth, this.contentHeight);
        }
    }

    @Override
    public void onZoomEnd(ZoomLayout view, float scale) {
        this.currentScale = scale;
        if (scale <= getMinScale()) {
            this.clearOnDoubleTapListeners();
        } else {
            this.addOnDoubleTapListener(this.zoomOnDoubleTapListener);
        }
        if (this.onCustomZoomListener != null) {
            this.onCustomZoomListener.onZoom(this.currentScale, this.containerWidth, this.containerHeight, this.contentWidth, this.contentHeight);
        }
    }

    @Override
    public void onLayout(int width, int height) {
        this.contentWidth = width;
        this.contentHeight = height;
    }

    public void setOnCustomZoomListener(OnCustomZoomListener onCustomZoomListener) {
        this.onCustomZoomListener = onCustomZoomListener;
    }

    public OnCustomZoomListener getOnCustomZoomListener() {
        return onCustomZoomListener;
    }

    interface OnCustomZoomListener {
        void onZoom(float zoom, float containerWidth, float containerHeight, float contentWidth, float contentHeight);
    }
}
