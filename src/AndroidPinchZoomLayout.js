import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import {Animated, findNodeHandle, NativeModules, requireNativeComponent, UIManager} from 'react-native';

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
                useScaleAnimatedEvents={!!onZoomScale}
                onZoomScale={onZoomScale}
                children={children}
            />
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
            animatedScale,
            onZoomScale
        } = props;
        if (animatedScale) {
            this._zoomScaleEvent = Animated.event(
                [{
                    nativeEvent: {
                        zoomScale: animatedScale,
                    }
                }],
                {
                    useNativeDriver: animatedNativeDriver,
                    listener: onZoomScale,
                }
            );
        }
    }

    render() {
        const {
            style,
            children,
            enable,
            minimumZoomScale,
            maximumZoomScale,
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
                minimumZoomScale={minimumZoomScale}
                maximumZoomScale={maximumZoomScale}
                onZoomScale={this._zoomScaleEvent || onZoomScale}
                verticalPanEnabled={verticalPanEnabled}
                horizontalPanEnabled={horizontalPanEnabled}
            >
                {children}
            </RenderComponent>
        );
    }

    zoomTo = (zoom, x, y, animate) => {
        UIManager.dispatchViewManagerCommand(
            findNodeHandle(this),
            UIManager.RNPinchZoomLayout.Commands.zoomTo,
            [zoom, x, y, animate],
        );
    }
}