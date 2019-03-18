import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import {NativeModules, requireNativeComponent} from 'react-native';

const {
    RNPinchZoomLayoutModule
} = NativeModules;

export default class PinchZoomLayout extends PureComponent {
    static propTypes = {
        enable: PropTypes.bool,
        minimumZoomScale: PropTypes.number,
        maximumZoomScale: PropTypes.number,
    };
    static defaultProps = {
        enable: true,
        minimumZoomScale: 1,
        maximumZoomScale: 3
    };

    render() {
        const {
            style,
            children,
            enable
        } = this.props;
        return (
            <PinchZoomLayoutAndroid
                style={style}
                enable={enable}
            >
                {children}
            </PinchZoomLayoutAndroid>
        );
    }
};

const cfg = {
    nativeOnly: {
        style: true,
        enable: true,
        minimumZoomScale: true,
        maximumZoomScale: true,
    }
};

const PinchZoomLayoutAndroid = requireNativeComponent('RNPinchZoomLayout', PinchZoomLayout, cfg);