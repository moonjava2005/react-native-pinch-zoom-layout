import React, {Component} from 'react';
import {Dimensions, Image, StyleSheet, View} from 'react-native';
import {SceneMap, TabView} from 'react-native-tab-view';
import PinchZoomLayout from 'react-native-pinch-zoom-layout';

const screenWidth = Dimensions.get('window').width;
const screenHeight = Dimensions.get('window').height;
const initialLayout = {width: screenWidth, height: screenHeight}
const FirstRoute = () => (
    <View style={[styles.scene, {backgroundColor: '#ff4081'}]}>
        <PinchZoomLayout
            style={styles.pinchZoomLayout}
            minimumZoomScale={1}
            maximumZoomScale={5}
        >
            <Image
                style={styles.image}
                source={{uri: 'http://123anhdep.net/wp-content/uploads/2016/04/tuyen-chon-hinh-anh-ngoc-trinh-dien-bikini-goi-cam-nong-bong-nhat-khoe-lan-da-trang-khong-ti-vet-2.jpg'}}
                resizeMode={'contain'}
            />
        </PinchZoomLayout>
    </View>
);
const SecondRoute = () => (
    <View style={[styles.scene, {backgroundColor: '#673ab7'}]}>
        <PinchZoomLayout
            style={styles.pinchZoomLayout}
            minimumZoomScale={1}
            maximumZoomScale={3}
        >
            <Image
                style={styles.image}
                source={{uri: 'http://123anhdep.net/wp-content/uploads/2016/04/tuyen-chon-hinh-anh-ngoc-trinh-dien-bikini-goi-cam-nong-bong-nhat-khoe-lan-da-trang-khong-ti-vet-3.jpg'}}
                resizeMode={'contain'}
            />
        </PinchZoomLayout>
    </View>
);
const ThirdRoute = () => (
    <View style={[styles.scene, {backgroundColor: '#44E496'}]}>
        <PinchZoomLayout
            style={styles.pinchZoomLayout}
            minimumZoomScale={1}
            maximumZoomScale={2}
        >
            <Image
                style={styles.image}
                source={{uri: 'http://123anhdep.net/wp-content/uploads/2016/04/tuyen-chon-hinh-anh-ngoc-trinh-dien-bikini-goi-cam-nong-bong-nhat-khoe-lan-da-trang-khong-ti-vet-12.jpg'}}
                resizeMode={'contain'}
            />
        </PinchZoomLayout>
    </View>
);

export default class App extends Component {
    state = {
        index: 0,
        routes: [
            {key: 'first', title: 'First'},
            {key: 'second', title: 'Second'},
            {key: 'third', title: 'Third'},
        ],
    };

    render() {
        return (
            <TabView
                navigationState={this.state}
                renderScene={SceneMap({
                    first: FirstRoute,
                    second: SecondRoute,
                    third: ThirdRoute,
                })}
                onIndexChange={index => this.setState({index})}
                initialLayout={initialLayout}
            />
        );
    }
}

const styles = StyleSheet.create({
    scene: {
        flex: 1,
        alignItems: 'stretch',
        justifyContent: 'center',
    },
    pinchZoomLayout: {
        flex: 1,
    },
    image: {
        width: screenWidth,
        height: screenWidth,
        backgroundColor: 'black'
    }
});