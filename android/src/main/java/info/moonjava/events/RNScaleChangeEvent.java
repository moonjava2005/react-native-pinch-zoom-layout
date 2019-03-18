package info.moonjava.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RNScaleChangeEvent extends Event {
    public static final String EVENT_NAME = "onTopRNScaleChange";
    private double zoomScale;
    private double containerWidth;
    private double containerHeight;
    private double contentWidth;
    private double contentHeight;

    public RNScaleChangeEvent(int viewTag, double zoomScale, double containerWidth, double containerHeight, double contentWidth, double contentHeight) {
        super(viewTag);
        this.zoomScale = zoomScale;
        this.containerWidth = containerWidth;
        this.containerHeight = containerHeight;
        this.contentWidth = contentWidth;
        this.contentHeight = contentHeight;
    }

    @Override
    public String getEventName() {
        return EVENT_NAME;
    }

    @Override
    public void dispatch(RCTEventEmitter rctEventEmitter) {
        rctEventEmitter.receiveEvent(this.getViewTag(), this.getEventName(), this.serializeEventData());
    }

    private WritableMap serializeEventData() {
        WritableMap eventData = Arguments.createMap();
        eventData.putInt("target", this.getViewTag());
        eventData.putDouble("zoomScale", this.zoomScale);
        eventData.putDouble("containerWidth", this.containerWidth);
        eventData.putDouble("containerHeight", this.containerHeight);
        eventData.putDouble("contentWidth", this.contentWidth);
        eventData.putDouble("contentHeight", this.contentHeight);
        return eventData;
    }
}
