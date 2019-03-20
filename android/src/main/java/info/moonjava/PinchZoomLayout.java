package info.moonjava;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;

import info.moonjava.events.RNScaleChangeEvent;


class PinchZoomLayout extends FrameLayout implements CustomPinchZoomLayout.OnCustomZoomListener {
    private EventDispatcher eventDispatcher;
    private CustomPinchZoomLayout zoomLayout;
    private PinchZoomContent zoomLayoutContent;
    private float _minScale = 1;
    private float _maxScale = 3;
    private int zoomDuration = 400;


    public PinchZoomLayout(@NonNull Context context) {
        super(context);
        this.init(context);
    }

    private void init(Context context) {
        this.eventDispatcher = ((ReactContext) context).getNativeModule(UIManagerModule.class).getEventDispatcher();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = inflater.inflate(R.layout.zoom_layout, this);
        zoomLayout = layoutView.findViewById(R.id.zoom_layout_container);
        this.setDefaultZoomLayoutConfig();
        if (zoomLayout != null) {
            zoomLayout.setOnCustomZoomListener(this);
//            this.zoomLayout.getEngine().addListener(this);
            zoomLayoutContent = zoomLayout.findViewById(R.id.zoom_layout_content);
            if (zoomLayoutContent != null) {
                zoomLayoutContent.setOnLayoutListener(zoomLayout);
            }
        }

    }

    private void setDefaultZoomLayoutConfig() {
        if (zoomLayout != null) {
            zoomLayout.setAllowOverScale(false);
            zoomLayout.setMinScale(this._minScale);
            zoomLayout.setMaxScale(this._maxScale);
            zoomLayout.setZoomDuration(this.zoomDuration);
//            zoomLayout.setAlignment(Alignment.CENTER);
//            zoomLayout.setTransformation(ZoomApi.TRANSFORMATION_CENTER_INSIDE, ZoomApi.TRANSFORMATION_GRAVITY_AUTO);
//            zoomLayout.setMinZoom(1.0f, ZoomApi.TYPE_ZOOM);
//            zoomLayout.setMaxZoom(3.0f, ZoomApi.TYPE_ZOOM);
//            zoomLayout.setZoomEnabled(true);
//            zoomLayout.setFlingEnabled(true);
//            zoomLayout.setHorizontalPanEnabled(true);
//            zoomLayout.setVerticalPanEnabled(true);
//            zoomLayout.setOverPinchable(false);
//            zoomLayout.setHasClickableChildren(true);
        }
    }

//    @Override
//    public boolean shouldDelayChildPressedState() {
//        return false;
//    }

//    @Override
//    public void onUpdate(@NotNull ZoomEngine engine, @NotNull Matrix matrix) {
////        super.onUpdate(engine, matrix);
//        float zoomLevel = engine.getZoom();
//        float containerWidth = engine.getContentWidth();
//        float containerHeight = engine.getContentHeight();
//        float containerWidth = engine.getContainerWidth();
//        float containerHeight = engine.getContainerHeight();
////        Log.d(PinchZoomLayoutManager.LOG_TAG, "Pinch Zoom Scale: " + zoomLevel + " Container: " + containerWidth + "x" + containerHeight + " Content: " + containerWidth + "x" + containerHeight);
//        eventDispatcher.dispatchEvent(new RNScaleChangeEvent(getId(), zoomLevel, containerWidth, containerHeight, containerWidth, containerHeight));
//    }

//    @Override
//    public void onIdle(@NotNull ZoomEngine zoomEngine) {
//
//    }

    View getContentChildAt(int index) {
        if (zoomLayoutContent != null) {
            return zoomLayoutContent.getChildAt(index);
        }
        return null;
    }

    int getContentChildCount() {
        if (zoomLayoutContent != null) {
            return zoomLayoutContent.getChildCount();
        }
        return 0;
    }

    void addChild(View child, int index) {
        if (zoomLayoutContent != null) {
//            child.setBackgroundColor(Color.BLUE);
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            child.setLayoutParams(params);
//            Log.d(PinchZoomLayoutManager.LOG_TAG, "Children size: " + child.getWidth() + "x" + child.getHeight());
            zoomLayoutContent.addView(child, index);
        }
    }

    void addChild(View child) {
        if (zoomLayoutContent != null) {
//            child.setBackgroundColor(Color.BLUE);
//            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            child.setLayoutParams(params);
//            Log.d(PinchZoomLayoutManager.LOG_TAG, "Children size: " + child.getWidth() + "x" + child.getHeight());
            zoomLayoutContent.addView(child);
        }
    }

    void removeContentView(View view) {
        if (zoomLayoutContent != null) {
            zoomLayoutContent.removeView(view);
        }
    }

    void removeContentViewAt(int index) {
        if (zoomLayoutContent != null) {
            zoomLayoutContent.removeViewAt(index);
        }
    }

    void removeAllContentViews() {
        if (zoomLayoutContent != null) {
            zoomLayoutContent.removeAllViews();
        }
    }

    void setMinZoom(float minZoom) {
        if (zoomLayout != null) {
//            zoomLayout.setMinZoom(minZoom, ZoomApi.TYPE_ZOOM);
            zoomLayout.setMinScale(minZoom);
            this._minScale = minZoom;
        }
    }

    void setMaxZoom(float maxZoom) {
        if (zoomLayout != null) {
//            zoomLayout.setMaxZoom(maxZoom, ZoomApi.TYPE_ZOOM);
            zoomLayout.setMaxScale(maxZoom);
            this._maxScale = maxZoom;
        }
    }

    void setZoomEnabled(boolean zoomEnabled) {
        if (zoomLayout != null) {
            zoomLayout.setAllowZoom(zoomEnabled);
//            zoomLayout.setZoomEnabled(zoomEnabled);
        }
    }

    void setPanEnabled(boolean panEnabled) {
        if (zoomLayout != null) {
//            zoomLayout.setHorizontalPanEnabled(panEnabled);
//            zoomLayout.setVerticalPanEnabled(panEnabled);
        }
    }

    void setHorizontalPanEnabled(boolean horizontalPanEnabled) {
        if (zoomLayout != null) {
//            zoomLayout.setHorizontalPanEnabled(horizontalPanEnabled);
        }
    }

    void setVerticalPanEnabled(boolean verticalPanEnabled) {
        if (zoomLayout != null) {
//            zoomLayout.setVerticalPanEnabled(verticalPanEnabled);
        }
    }

    void zoomTo(double zoom, double x, double y, boolean animated) {
        if (zoomLayout != null) {
            zoomLayout.setScale((float) zoom, (float) x, (float) y, animated);
        }
    }

    void setZoomDuration(int zoomDuration) {
        this.zoomDuration = zoomDuration;
        if (zoomLayout != null) {
            zoomLayout.setZoomDuration(this.zoomDuration);
        }
    }

    @Override
    public void onZoom(float zoom, float containerWidth, float containerHeight, float contentWidth, float contentHeight) {
        eventDispatcher.dispatchEvent(new RNScaleChangeEvent(getId(), zoom, containerWidth, containerHeight, contentWidth, contentHeight));
    }
}
