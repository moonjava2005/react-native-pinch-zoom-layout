package info.moonjava;

import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.otaliastudios.zoom.Alignment;
import com.otaliastudios.zoom.ZoomApi;
import com.otaliastudios.zoom.ZoomEngine;
import com.otaliastudios.zoom.ZoomLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import info.moonjava.events.RNScaleChangeEvent;

class PinchZoomLayout extends FrameLayout implements ZoomEngine.Listener {
    private EventDispatcher mEventDispatcher;
    private ZoomLayout zoomLayout;
    private PinchZoomContent zoomLayoutContent;
    private int layoutWidth = 0;
    private int layoutHeight = 0;

    public PinchZoomLayout(@NotNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public PinchZoomLayout(@NotNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public PinchZoomLayout(@NotNull Context context) {
        super(context);
        this.init(context);
    }

    private void init(Context context) {
//        this.getEngine().addListener(this);
        this.mEventDispatcher = ((ReactContext) context).getNativeModule(UIManagerModule.class).getEventDispatcher();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = inflater.inflate(R.layout.zoom_layout, this);
        zoomLayout = layoutView.findViewById(R.id.zoom_layout_container);
        this.setDefaultZoomLayoutConfig();
        if (zoomLayout != null) {
            zoomLayoutContent = zoomLayout.findViewById(R.id.zoom_layout_content);
        }

    }

    private void setDefaultZoomLayoutConfig() {
        if (zoomLayout != null) {
            zoomLayout.setAlignment(Alignment.CENTER);
            zoomLayout.setTransformation(ZoomApi.TRANSFORMATION_CENTER_INSIDE);
            zoomLayout.setMinZoom(1.0f, ZoomApi.TYPE_ZOOM);
            zoomLayout.setMaxZoom(3.0f, ZoomApi.TYPE_ZOOM);
            zoomLayout.setZoomEnabled(true);
            zoomLayout.setFlingEnabled(true);
            zoomLayout.setHorizontalPanEnabled(true);
            zoomLayout.setVerticalPanEnabled(true);
            zoomLayout.setOverPinchable(true);
            zoomLayout.setHasClickableChildren(true);
        }
    }

    @Override
    public void onUpdate(@NotNull ZoomEngine engine, @NotNull Matrix matrix) {
//        super.onUpdate(engine, matrix);
        float zoomLevel = engine.getZoom();
        mEventDispatcher.dispatchEvent(new RNScaleChangeEvent(getId(), zoomLevel));
    }

    @Override
    public void onIdle(@NotNull ZoomEngine zoomEngine) {

    }

    public void addChild(View child, int index) {
        if (zoomLayoutContent != null) {
            zoomLayoutContent.addView(child, index);
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

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        this.layoutWidth = (right - left);
        this.layoutHeight = (bottom - top);
        super.onLayout(changed, left, top, right, bottom);
    }
}
