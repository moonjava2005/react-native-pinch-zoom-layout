package info.moonjava;

import android.view.View;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;

class PinchZoomLayoutManager extends SimpleViewManager {
    private static final String REACT_CLASS = "PinchZoomLayout";

    PinchZoomLayoutManager(ReactContext reactContext) {

    }

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected View createViewInstance(ThemedReactContext reactContext) {
        return null;
    }
}
