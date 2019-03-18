import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import {Animated, NativeModules, requireNativeComponent} from 'react-native';

const {
    RNPinchZoomLayoutModule
} = NativeModules;

const RNPinchZoomLayout = requireNativeComponent('RNPinchZoomLayout', null);

class PurePinchZoomLayout extends PureComponent {

    render() {
        const {
            style,
            children,
            enable,
            verticalPanEnabled,
            horizontalPanEnabled,
            onZoomScale,
        } = this.props;
        return (
            <RNPinchZoomLayout
                style={style}
                enable={enable}
                verticalPanEnabled={verticalPanEnabled}
                horizontalPanEnabled={horizontalPanEnabled}
                onZoomScale={onZoomScale}
            >
                {children}
            </RNPinchZoomLayout>
        );
    }
}

const AnimatedWrappedPinchZoomLayout = Animated.createAnimatedComponent(PurePinchZoomLayout);

export default class AndroidPinchZoomLayout extends PureComponent {
    static propTypes = {
        animatedScale: PropTypes.object,
        animatedNativeDriver: PropTypes.bool,
        enable: PropTypes.bool,
        verticalPanEnabled: PropTypes.bool,
        horizontalPanEnabled: PropTypes.bool,
        minimumZoomScale: PropTypes.number,
        maximumZoomScale: PropTypes.number,
        onZoomScale: PropTypes.func,
    };
    static defaultProps = {
        animatedNativeDriver: true,
        enable: true,
        verticalPanEnabled: true,
        horizontalPanEnabled: true,
        minimumZoomScale: 1,
        maximumZoomScale: 3
    };
    _zoomScaleEvent;

    constructor(props) {
        super(props);
        const {
            animatedNativeDriver,
            animatedScale
        } = props;
        if (animatedScale) {
            this._zoomScaleEvent = Animated.event(
                [{
                    nativeEvent: {
                        zoomScale: animatedScale,
                    }
                }],
                {useNativeDriver: animatedNativeDriver}
            );
        }
    }

    render() {
        const {
            style,
            children,
            enable,
            verticalPanEnabled,
            horizontalPanEnabled,
            onZoomScale
        } = this.props;
        const useAnimatedEvents = !!this._zoomScaleEvent;
        const RenderComponent = useAnimatedEvents ? AnimatedWrappedPinchZoomLayout : PurePinchZoomLayout;
        return (
            <RenderComponent
                style={style}
                enable={enable}
                onZoomScale={useAnimatedEvents ? this._zoomScaleEvent : onZoomScale}
                useAnimatedEvents={useAnimatedEvents}
                verticalPanEnabled={verticalPanEnabled}
                horizontalPanEnabled={horizontalPanEnabled}
            >
                {children}
            </RenderComponent>
        );
    }
}