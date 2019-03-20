
# react-native-pinch-zoom-layout

## Getting started

`$ npm install react-native-pinch-zoom-layout --save`

Or

`$ yarn add react-native-pinch-zoom-layout`

### Mostly automatic installation

`$ react-native link react-native-pinch-zoom-layout`

### Manual installation


#### iOS

No action required

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import info.moonjava.RNPinchZoomLayoutPackage;` to the imports at the top of the file
  - Add `new RNPinchZoomLayoutPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
  	```
  	include ':react-native-pinch-zoom-layout'
  	project(':react-native-pinch-zoom-layout').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-pinch-zoom-layout/android')
  	```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
  	```
      implementation project(':react-native-pinch-zoom-layout')
  	```

## Dependencies
`react-native-pinch-zoom-layout` uses [react-native-gesture-handler](https://github.com/kmagiera/react-native-gesture-handler)! Link it before using

## Usage
```javascript
import React,{Component} from 'react';
import PinchZoomLayout from 'react-native-pinch-zoom-layout';
import {
    Image
} from 'react-native';

export default class ImageViewer extends Component {
    render() {
       return(<PinchZoomLayout
                          style={Device.isIos ? FULL_FLEX : FULL_FLEX_CENTER_ALL}
                          onZoom={this.onZoom}
                      >
                          <Image
                                style={{width:56,height:56}}
                                source={{
                                  uri:'https://facebook.github.io/react-native/img/header_logo.png'
                              }}
                          />
                      </PinchZoomLayout>)
    }
    onZoom=event=>{
        const {
            containerWidth,
            containerHeight,
            contentWidth,
            contentHeight,
            zoomScale
        }=event;
    }
}
```

### Configurable props
* [enabled](#enabled)
* [panEnabled](#panEnabled)
* [minimumZoomScale](#minimumZoomScale)
* [maximumZoomScale](#maximumZoomScale)

### Event props
* [onZoom](#onZoom)
* [onTap](#onTap)

### Methods
* [zoom](#zoom)


### Configurable props

#### enabled
Indicates whether the layout allows zoom.
* **true (default)** - allow zoom
* **false** -  Disable zoom

#### panEnabled
Indicates whether the layout can pan when the zoomed content is bigger than the container.
* **true (default)** - Allow pan
* **false** - Disable pan

#### minimumZoomScale
The minimum zoom level.
* **default** - 1

#### maximumZoomScale
The maximum zoom level.
* **default** - 3