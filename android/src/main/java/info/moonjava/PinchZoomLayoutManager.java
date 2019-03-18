package info.moonjava;

import android.view.View;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.otaliastudios.zoom.ZoomApi;

import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

class PinchZoomLayoutManager extends ViewGroupManager<PinchZoomLayout> {
    private static final String REACT_CLASS = "RNPinchZoomLayout";
    public static final String LOG_TAG = "RNPinchZoomLayout";

    PinchZoomLayoutManager(ReactContext reactContext) {

    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected PinchZoomLayout createViewInstance(ThemedReactContext reactContext) {
        return new PinchZoomLayout(reactContext);
    }

    @ReactProp(name = "enable", defaultBoolean = true)
    public void setZoomEnable(PinchZoomLayout pinchZoomLayout, boolean zoomEnabled) {
        pinchZoomLayout.setZoomEnabled(zoomEnabled);
    }

    @ReactProp(name = "minimumZoomScale", defaultFloat = 1.0f)
    public void setMinimumZoomScale(PinchZoomLayout pinchZoomLayout, float minimumZoomScale) {
        pinchZoomLayout.setMinZoom(minimumZoomScale);
    }

    @ReactProp(name = "maximumZoomScale", defaultFloat = 3.0f)
    public void setMaximumZoomScale(PinchZoomLayout view, float maximumZoomScale) {
        view.setMaxZoom(maximumZoomScale);
    }

    @Override
    public @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
                "zoomLayoutScale", MapBuilder.of("registrationName", "onZoomLayoutScale")
        );
    }

    @Override
    protected void onAfterUpdateTransaction(PinchZoomLayout view) {
        super.onAfterUpdateTransaction(view);
    }

    @Override
    public void addView(PinchZoomLayout parent, View child, int index) {
        parent.addChild(child, index);
    }

    @Override
    public void addViews(PinchZoomLayout parent, List<View> views) {
    }
}
