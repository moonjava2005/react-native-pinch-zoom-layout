package info.moonjava;

import android.content.Context;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.otaliastudios.zoom.ZoomApi;
import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;

import org.jetbrains.annotations.NotNull;

import info.moonjava.events.RNScaleChangeEvent;

class PinchZoomLayout extends FrameLayout implements ZoomEngine.Listener {
    private EventDispatcher mEventDispatcher;
    private ZoomLayout zoomLayout;
    private PinchZoomContent zoomLayoutContent;

    public PinchZoomLayout(@NotNull Context context) {
        super(context);
        this.init(context);
    }

    private void init(Context context) {
        this.mEventDispatcher = ((ReactContext) context).getNativeModule(UIManagerModule.class).getEventDispatcher();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = inflater.inflate(R.layout.zoom_layout, this);
        zoomLayout = layoutView.findViewById(R.id.zoom_layout_container);
        this.setDefaultZoomLayoutConfig();
        if (zoomLayout != null) {
            this.zoomLayout.getEngine().addListener(this);
            zoomLayoutContent = zoomLayout.findViewById(R.id.zoom_layout_content);
        }

    }

    private void setDefaultZoomLayoutConfig() {
        if (zoomLayout != null) {
            Log.d(PinchZoomLayoutManager.LOG_TAG, "******Init Layout*****");
//            zoomLayout.setAlignment(Alignment.CENTER);
//            zoomLayout.setTransformation(ZoomApi.TRANSFORMATION_CENTER_INSIDE, ZoomApi.TRANSFORMATION_GRAVITY_AUTO);
            zoomLayout.setMinZoom(1.0f, ZoomApi.TYPE_ZOOM);
            zoomLayout.setMaxZoom(3.0f, ZoomApi.TYPE_ZOOM);
            zoomLayout.setZoomEnabled(true);
            zoomLayout.setFlingEnabled(true);
            zoomLayout.setHorizontalPanEnabled(true);
            zoomLayout.setVerticalPanEnabled(true);
            zoomLayout.setOverPinchable(false);
            zoomLayout.setHasClickableChildren(true);
        }
    }

//    @Override
//    public boolean shouldDelayChildPressedState() {
//        return false;
//    }

    @Override
    public void onUpdate(@NotNull ZoomEngine engine, @NotNull Matrix matrix) {
//        super.onUpdate(engine, matrix);
        float zoomLevel = engine.getZoom();
        float contentWidth = engine.getContentWidth();
        float contentHeight = engine.getContentHeight();
        float containerWidth = engine.getContainerWidth();
        float containerHeight = engine.getContainerHeight();
//        Log.d(PinchZoomLayoutManager.LOG_TAG, "Pinch Zoom Scale: " + zoomLevel + " Container: " + containerWidth + "x" + containerHeight + " Content: " + contentWidth + "x" + contentHeight);
        mEventDispatcher.dispatchEvent(new RNScaleChangeEvent(getId(), zoomLevel, containerWidth, containerHeight, contentWidth, contentHeight));
    }

    @Override
    public void onIdle(@NotNull ZoomEngine zoomEngine) {

    }

    View getContentChildAt(int index) {
        Log.d(PinchZoomLayoutManager.LOG_TAG, "getContentChildAt: " + index);
        if (zoomLayoutContent != null) {
            return zoomLayoutContent.getChildAt(index);
        }
        return null;
    }

    int getContentChildCount() {
        Log.d(PinchZoomLayoutManager.LOG_TAG, "getContentChildCount");
        if (zoomLayoutContent != null) {
            return zoomLayoutContent.getChildCount();
        }
        return 0;
    }

    void addChild(View child, int index) {
        Log.d(PinchZoomLayoutManager.LOG_TAG, "addChild at index: " + index);
        if (zoomLayoutContent != null) {
            zoomLayoutContent.addView(child, index);
        }
    }

    void addChild(View child) {
        Log.d(PinchZoomLayoutManager.LOG_TAG, "addChild");
        if (zoomLayoutContent != null) {
            zoomLayoutContent.addView(child);
        }
    }

    void removeContentView(View view) {
        Log.d(PinchZoomLayoutManager.LOG_TAG, "removeContentView");
        if (zoomLayoutContent != null) {
            zoomLayoutContent.removeView(view);
        }
    }

    void removeContentViewAt(int index) {
        Log.d(PinchZoomLayoutManager.LOG_TAG, "removeContentViewAt: " + index);
        if (zoomLayoutContent != null) {
            zoomLayoutContent.removeViewAt(index);
        }
    }

    void removeAllContentViews() {
        Log.d(PinchZoomLayoutManager.LOG_TAG, "removeAllContentViews");
        if (zoomLayoutContent != null) {
            zoomLayoutContent.removeAllViews();
        }
    }

    void setMinZoom(float minZoom) {
        if (zoomLayout != null) {
            zoomLayout.setMinZoom(minZoom, ZoomApi.TYPE_ZOOM);
        }
    }

    void setMaxZoom(float maxZoom) {
        if (zoomLayout != null) {
            zoomLayout.setMaxZoom(maxZoom, ZoomApi.TYPE_ZOOM);
        }
    }

    void setZoomEnabled(boolean zoomEnabled) {
        if (zoomLayout != null) {
            zoomLayout.setZoomEnabled(zoomEnabled);
        }
    }

    void setPanEnabled(boolean panEnabled) {
        if (zoomLayout != null) {
            zoomLayout.setHorizontalPanEnabled(panEnabled);
            zoomLayout.setVerticalPanEnabled(panEnabled);
        }
    }

    void setHorizontalPanEnabled(boolean horizontalPanEnabled) {
        if (zoomLayout != null) {
            zoomLayout.setHorizontalPanEnabled(horizontalPanEnabled);
        }
    }

    void setVerticalPanEnabled(boolean verticalPanEnabled) {
        if (zoomLayout != null) {
            zoomLayout.setVerticalPanEnabled(verticalPanEnabled);
        }
    }
}
