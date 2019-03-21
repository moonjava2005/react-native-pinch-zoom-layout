import React, {PureComponent} from 'react';
import PropTypes from 'prop-types';
import {Platform, ScrollView, StyleSheet, View} from 'react-native';
import {State, TapGestureHandler} from 'react-native-gesture-handler';
import AndroidPinchZoomLayout from './AndroidPinchZoomLayout';

export default class PinchZoomLayout extends PureComponent {
    static propTypes = {
        zoomDuration: PropTypes.number,
        minimumZoomScale: PropTypes.number,
        maximumZoomScale: PropTypes.number,
        enabled: PropTypes.bool,
        panEnabled: PropTypes.bool,
        onZoom: PropTypes.func,
        onTap: PropTypes.func,
    };
    static defaultProps = {
        enabled: true,
        panEnabled: true,
        minimumZoomScale: 1,
        maximumZoomScale: 3,
    };


    scrollViewRef = React.createRef();
    pinchZoomRef = React.createRef();
    singleTapRef = React.createRef();
    doubleTapRef = React.createRef();
    _currentScale = 0;
    _containerWidth;
    _containerHeight;
    _contentWidth;
    _contentHeight;

    constructor(props) {
        super(props);
        this.state = {
            verticalPanEnabled: false,
            horizontalPanEnabled: false,
        };
    }

    render() {
        const {
            style,
            children,
            zoomDuration,
            minimumZoomScale,
            maximumZoomScale
        } = this.props;
        const {
            verticalPanEnabled,
            horizontalPanEnabled,
        } = this.state;
        if (Platform.OS === 'ios') {
            return (<ScrollView
                    style={style}
                    onLayout={this.onLayout}
                    contentContainerStyle={styles.contentContainerStyle}
                    ref={this.scrollViewRef}
                    centerContent={true}
                    scrollEnabled={verticalPanEnabled || horizontalPanEnabled}
                    minimumZoomScale={minimumZoomScale}
                    maximumZoomScale={maximumZoomScale}
                    showsHorizontalScrollIndicator={false}
                    showsVerticalScrollIndicator={false}
                    scrollEventThrottle={32}
                    pinchGestureEnabled={true}
                    onScrollEndDrag={this.onScrollEndDrag}
                    onContentSizeChange={this.onContentSizeChange}
                >
                    <TapGestureHandler
                        ref={this.singleTapRef}
                        onHandlerStateChange={this._onSingleTap}
                        waitFor={this.doubleTapRef}>
                        <TapGestureHandler
                            ref={this.doubleTapRef}
                            onHandlerStateChange={this._onDoubleTap}
                            numberOfTaps={2}
                        >
                            <View
                            >
                                {children}
                            </View>
                        </TapGestureHandler>
                    </TapGestureHandler>
                </ScrollView>
            );
        }
        return (
            <AndroidPinchZoomLayout
                style={[style, styles.container]}
                ref={this.pinchZoomRef}
                zoomDuration={zoomDuration}
                minimumZoomScale={minimumZoomScale}
                maximumZoomScale={maximumZoomScale}
                onZoomScale={this.onZoomScale}
                verticalPanEnabled={verticalPanEnabled}
                horizontalPanEnabled={horizontalPanEnabled}
            >
                <TapGestureHandler
                    ref={this.singleTapRef}
                    onHandlerStateChange={this._onSingleTap}
                    waitFor={this.doubleTapRef}>
                    <TapGestureHandler
                        ref={this.doubleTapRef}
                        onHandlerStateChange={this._onDoubleTap}
                        numberOfTaps={2}
                    >
                        <View
                        >
                            {children}
                        </View>
                    </TapGestureHandler>
                </TapGestureHandler>
            </AndroidPinchZoomLayout>
        );
    }


    _onSingleTap = (event) => {
        if (event.nativeEvent.state === State.ACTIVE) {
            const {
                onTap
            } = this.props;
            onTap && onTap();
        }
    };


    _onDoubleTap = ({nativeEvent}) => {
        const {
            state,
            x,
            y,
            absoluteX,
            absoluteY,
        } = nativeEvent;
        if (state === State.ACTIVE) {
            const {
                minimumZoomScale,
                maximumZoomScale,
            } = this.props;
            let nextScale = minimumZoomScale;
            if (this._currentScale >= maximumZoomScale) {
                nextScale = minimumZoomScale;
            } else {
                nextScale = maximumZoomScale;
            }
            if (nextScale !== this._currentScale) {
                if (Platform.OS === 'ios') {
                    if (this._contentWidth > 0 && this._contentHeight > 0) {
                        const nextWidth = this._contentWidth / nextScale;
                        const nextHeight = this._contentHeight / nextScale;
                        const fromLeftEdge = absoluteX / nextScale;
                        const fromTopEdge = absoluteY / nextScale;
                        if (this.scrollViewRef.current) {
                            this.scrollViewRef.current.getScrollResponder().scrollResponderZoomTo({
                                x: x - fromLeftEdge,
                                y: y - fromTopEdge,
                                width: nextWidth,
                                height: nextHeight
                            });
                        }
                    }
                } else {
                    if (this.pinchZoomRef.current) {
                        this.pinchZoomRef.current.zoomTo(nextScale, absoluteX, absoluteY, true);
                    }
                }
            }
        }
    };

    onLayout = event => {
        const {
            width,
            height
        } = event.nativeEvent.layout;
        this._containerWidth = width;
        this._containerHeight = height;
    };

    onContentSizeChange = (contentWidth, contentHeight) => {
        this._contentWidth = contentWidth;
        this._contentHeight = contentHeight;
    };

    onScrollEndDrag = event => {
        const {
            zoomScale,
        } = event.nativeEvent;
        this._processZoomScale({
            zoomScale,
            contentWidth: this._contentWidth,
            contentHeight: this._contentHeight,
            containerWidth: this._containerWidth,
            containerHeight: this._containerHeight
        });
    };
    onZoomScale = event => {
        const {
            containerWidth,
            containerHeight,
            contentWidth,
            contentHeight,
            zoomScale
        } = event.nativeEvent;
        this._processZoomScale({zoomScale, contentWidth, contentHeight, containerWidth, containerHeight});
    };

    _processZoomScale = ({zoomScale, contentWidth, contentHeight, containerWidth, containerHeight}) => {
        const {
            onZoom,
            minimumZoomScale,
            panEnabled
        } = this.props;
        if (zoomScale !== this._currentScale) {
            this._currentScale = zoomScale;
            let nextState = null;
            if (panEnabled) {
                if (this._currentScale > minimumZoomScale) {
                    let verticalPanEnabled = false;
                    let horizontalPanEnabled = false;
                    const scaledWidth = contentWidth * this._currentScale;
                    const scaledHeight = contentHeight * this._currentScale;
                    if (scaledHeight > containerHeight) {
                        verticalPanEnabled = true;
                    }
                    if (scaledWidth > containerWidth) {
                        horizontalPanEnabled = true;
                    }
                    if (!nextState) {
                        nextState = {};
                    }
                    nextState = {
                        ...nextState,
                        verticalPanEnabled: verticalPanEnabled,
                        horizontalPanEnabled: horizontalPanEnabled
                    };
                } else {
                    if (!nextState) {
                        nextState = {};
                    }
                    nextState = {
                        ...nextState,
                        verticalPanEnabled: false,
                        horizontalPanEnabled: false,
                    };
                }
            }
            this._contentWidth = contentWidth;
            this._contentHeight = contentHeight;
            if (nextState) {
                this.setState(nextState);
            }
            onZoom && onZoom({
                zoomScale,
                containerWidth: containerWidth,
                containerHeight: containerHeight,
                contentWidth: contentWidth,
                contentHeight: contentHeight,
            });
        }
    };
    zoom = ({zoomScale, animated}) => {
        if (Platform.OS === 'ios') {
            const nextWidth = this._contentWidth / zoomScale;
            const nextHeight = this._contentHeight / zoomScale;
            if (this.scrollViewRef.current) {
                this.scrollViewRef.current.getScrollResponder().scrollResponderZoomTo({
                    width: nextWidth,
                    height: nextHeight,
                    animated: animated
                });
            }
        } else {
            if (this.pinchZoomRef.current) {
                this.pinchZoomRef.current.zoom(zoomScale, true);
            }
        }
    }
}
const styles = StyleSheet.create({
    contentContainerStyle: {
        justifyContent: 'center'
    },
    stretchContainerStyle: {
        alignItems: 'center'
    },
    container: {
        alignItems: 'stretch',
        justifyContent: 'center'
    }
});