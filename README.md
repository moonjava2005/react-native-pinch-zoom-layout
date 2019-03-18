
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


## Usage
```javascript
import PinchZoomLayout from 'react-native-pinch-zoom-layout';

// TODO: What to do with the module?
PinchZoomLayout;
```
  