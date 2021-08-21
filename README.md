# React-Native Scanner

## Installation 

`yarn add https://github.com/Splittr-App/react-native-document-scanner.git`

## Android

Edit /android/app/src/main/AndroidManifest.xml

1. Add `xmlns:tools="http://schemas.android.com/tools"` property to `<manifest/>` element
2. Add child `<uses-permission android:name="android.permission.CAMERA" />`to `<manifest/>` element
3. Add `tools:replace="android:allowBackup"` property to `<application/>` element

Edit settings.gradle

Add 
```
include ':openCVLibrary310'
project(':openCVLibrary310').projectDir = new File(rootProject.projectDir,'../node_modules/@splittr-app/react-native-scanner/android/openCVLibrary310')
```

## Usage

```javascript
import React, { Component, useRef } from "react"
import { View, Image } from "react-native"

import DocumentScanner from "@splittr-app/react-native-document-scanner"

function YourComponent(props) {
  return (
    <View>
      <DocumentScanner
        style={styles.scanner}
        onPictureTaken={handleOnPictureTaken}
        overlayColor="rgba(255,130,0, 0.7)"
        enableTorch={false}
        quality={0.5}
        detectionCountBeforeCapture={5}
        detectionRefreshRateInMS={50}
      />
    </View>
  )
}
```

Full example in [example folder](https://github.com/Woonivers/react-native-document-scanner/tree/master/example).

## Properties

| Prop                        | Platform | Default |   Type    | Description                                                         |
| :-------------------------- | :------: | :-----: | :-------: | :------------------------------------------------------------------ |
| overlayColor                |   Both   | `none`  | `string`  | Color of the detected rectangle : rgba recommended                  |
| detectionCountBeforeCapture |   Both   |   `5`   | `integer` | Number of correct rectangle to detect before capture                |
| detectionRefreshRateInMS    |   iOS    |  `50`   | `integer` | Time between two rectangle detection attempt                        |
| enableTorch                 |   Both   | `false` |  `bool`   | Allows to active or deactivate flash during document detection      |
| useFrontCam                 |   iOS    | `false` |  `bool`   | Allows you to switch between front and back camera                  |
| brightness                  |   iOS    |   `0`   |  `float`  | Increase or decrease camera brightness. Normal as default.          |
| saturation                  |   iOS    |   `1`   |  `float`  | Increase or decrease camera saturation. Set `0` for black & white   |
| contrast                    |   iOS    |   `1`   |  `float`  | Increase or decrease camera contrast. Normal as default             |
| quality                     |   iOS    |  `0.8`  |  `float`  | Image compression. Reduces both image size and quality              |
| useBase64                   |   iOS    | `false` |  `bool`   | If base64 representation should be passed instead of image uri's    |
| saveInAppDocument           |   iOS    | `false` |  `bool`   | If should save in app document in case of not using base 64         |
| captureMultiple             |   iOS    | `false` |  `bool`   | Keeps the scanner on after a successful capture                     |
| saveOnDevice                | Android  | `false` |  `bool`   | Save the image in the device storage (**Need storage permissions**) |

## Manual capture

- First create a mutable ref object:

```javascript
const documentScannerElement = useRef(null)
```

- Pass a ref object to your component:

```javascript
<DocumentScanner ref={documentScannerElement} />
```

- Then call:

```javascript
documentScannerElement.current.capture()
```

## Each rectangle detection (iOS only) _-Non tested-_

| Props             | Params                                 | Type     | Description |
| ----------------- | -------------------------------------- | -------- | ----------- |
| onRectangleDetect | `{ stableCounter, lastDetectionType }` | `object` | See below   |

The returned object includes the following keys :

- `stableCounter`

Number of correctly formated rectangle found (this number triggers capture once it goes above `detectionCountBeforeCapture`)

- `lastDetectionType`

Enum (0, 1 or 2) corresponding to the type of rectangle found

0. Correctly formated rectangle
1. Wrong perspective, bad angle
1. Too far

## Returned image

| Prop           | Params |   Type   | Description                                                                                                                                                                         |
| :------------- | :----: | :------: | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| onPictureTaken | `data` | `object` | Returns the captured image in an object `{ croppedImage: ('URI or BASE64 string'), initialImage: 'URI or BASE64 string', rectangleCoordinates[only iOS]: 'object of coordinates' }` |
