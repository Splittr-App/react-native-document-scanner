package com.documentscanner;

import android.app.Activity;
import com.documentscanner.views.MainView;
import com.documentscanner.views.ScannerView;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.annotations.ReactProp;
import javax.annotation.Nullable;
import java.util.Map;

public class DocumentScannerViewManager extends ViewGroupManager<MainView> {
    private static final String REACT_CLASS = "RNPdfScanner";
    private MainView view = null;

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    protected MainView createViewInstance(final ThemedReactContext reactContext) {
        MainView.createInstance(reactContext, (Activity) reactContext.getBaseContext());
        view = MainView.getInstance();

        view.setOnProcessingListener(new ScannerView.OnProcessingListener() {
            @Override
            public void onProcessingChange(WritableMap data) {
                dispatchEvent(reactContext, "onProcessingChange", data);
            }
        });

        view.setOnScannerListener(new ScannerView.OnScannerListener() {
            @Override
            public void onPictureTaken(WritableMap data) {
                dispatchEvent(reactContext, "onPictureTaken", data);
            }
        });
        return view;
    }

    @ReactProp(name = "capturedQuality", defaultDouble = 0.5)
    public void setCapturedQuality(MainView view, double quality) {
        view.setCapturedQuality(quality);
    }

    @ReactProp(name = "filterId", defaultInt = 1)
    public void setFilterId(MainView view, int filterId) {
        view.setFilterId(filterId);
    }

    @ReactProp(name = "saveOnDevice", defaultBoolean = false)
    public void setSaveOnDevice(MainView view, Boolean saveOnDevice) {
        view.setSaveOnDevice(saveOnDevice);
    }

    @ReactProp(name = "detectionCountBeforeCapture", defaultInt = 15)
    public void setDetectionCountBeforeCapture(MainView view, int numberOfRectangles) {
        view.setDetectionCountBeforeCapture(numberOfRectangles);
    }

    @ReactProp(name = "durationBetweenCaptures", defaultDouble = 0)
    public void setDurationBetweenCaptures(MainView view, double durationBetweenCaptures) {
        view.setDurationBetweenCaptures(durationBetweenCaptures);
    }

    @ReactProp(name = "enableTorch", defaultBoolean = false)
    public void setEnableTorch(MainView view, Boolean enable) {
        view.setEnableTorch(enable);
    }

    @ReactProp(name = "manualOnly", defaultBoolean = false)
    public void setManualOnly(MainView view, Boolean manualOnly) {
        view.setManualOnly(manualOnly);
    }

    @ReactProp(name = "brightness", defaultDouble = 10)
    public void setBrightness(MainView view, double brightness) {
        view.setBrightness(brightness);
    }

    @ReactProp(name = "contrast", defaultDouble = 1)
    public void setContrast(MainView view, double contrast) {
        view.setContrast(contrast);
    }

    // TODO: This will be replaced with border color
    @ReactProp(name = "overlayColor")
    public void setOverlayColor(MainView view, String rgbaColor) {
        view.setOverlayColor(rgbaColor);
    }

    @ReactProp(name = "showBorder", defaultBoolean = false)
    public void setShowBorder(MainView view, Boolean enable) {
        view.setShowBorder(enable);
    }

    @ReactProp(name = "faceDetection", defaultBoolean = false)
    public void setFaceDetection(MainView view, Boolean enable) {
        view.setFaceDetection(enable);
    }

    // Use only when face detection is on
    @ReactProp(name = "cropHeight", defaultDouble = 0)
    public void setPhotoHeight(MainView view, double height) {
        view.setPhotoHeight(height);
    }

    // Use only when face detection is on
    @ReactProp(name = "cropWidth", defaultDouble = 0)
    public void setPhotoWidth(MainView view, double width) {
        view.setPhotoWidth(width);
    }

    // Double page scan (like passport)
    @ReactProp(name = "doublePageScan", defaultBoolean = false)
    public void setDoublePageScan(MainView view, Boolean enable) {
        view.setDoublePageScan(enable);
    }

    @Override
    public @Nullable Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
            "onDeviceSetup", MapBuilder.of("registrationName", "onDeviceSetup"),
            "onTorchChanged", MapBuilder.of("registrationName", "onTorchChanged"),
            "onRectangleDetected", MapBuilder.of("registrationName", "onRectangleDetected"),

            // onPictureTaken is conflicting with existing function and never been triggered
            "onPictureTaken", MapBuilder.of("registrationName", "onPictureTaken"),
            "onPictureProcessed", MapBuilder.of("registrationName", "onPictureProcessed"),
            "onErrorProcessingImage", MapBuilder.of("registrationName", "onErrorProcessingImage")
        );
    }

    private void dispatchEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
