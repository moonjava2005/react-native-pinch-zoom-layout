package info.moonjava.events;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class RNScaleChangeEvent extends Event {
    public static final String EVENT_NAME = "onTopRNScaleChange";
    private double scale;

    public RNScaleChangeEvent(int viewTag, double scale) {
        super(viewTag);
        this.scale = scale;
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
        eventData.putDouble("scale", this.scale);
        eventData.putInt("target", this.getViewTag());
        return eventData;
    }
}
