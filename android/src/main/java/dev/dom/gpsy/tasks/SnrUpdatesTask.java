package dev.dom.gpsy.tasks;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;

import android.content.Context;
import io.flutter.plugin.common.PluginRegistry;

import dev.dom.gpsy.data.SnrOptions;
import android.app.Activity;
import io.flutter.plugin.common.MethodChannel.Result;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;
import android.util.Log;



public class SnrUpdatesTask {
    private final Context mAndroidContext;
    private final Result mResult;
    private float mSatSnr;
    LocationManager mLocationManager;
    GnssStatus.Callback mGnssStatusCallback;

    public SnrUpdatesTask(
            PluginRegistry.Registrar registrar,
            Result result
    ) {
        mResult = result;
        mAndroidContext = registrar.activity() != null ? registrar.activity() : registrar.activeContext();
        atStart();
    }

    public void startTask() {
        reportLocationUpdate(mSatSnr);
    }
//
//    @Override
//    public void stopTask() {
//        mLocationManager.unregisterGnssStatusCallback(
//                mGnssStatusCallback
//        );
//        super.stopTask();
//    }

//    private void handleError() {
//        getTaskContext().getResult().error(
//                "INVALID_LOCATION_SETTINGS",
//                "Location settings are inadequate, check your location settings.",
//                null);
//    }


    private void atStart() {
        System.out.print("[SnrUpdatesTask atStart]");
        Log.d("Plugin", "SnrUpdatesTask atStart()");
        mLocationManager = (LocationManager) mAndroidContext.getSystemService(Context.LOCATION_SERVICE);
        mGnssStatusCallback = new GnssStatus.Callback() {
            @Override
            public void onSatelliteStatusChanged(GnssStatus status) {
                System.out.println("Callback");
                Log.d("Plugin", "SatellitesCountTask callback");
                int count = status.getSatelliteCount();
                System.out.println(count);
                if (count >= 0) {
                    mSatSnr = status.getCn0DbHz(count);
                    System.out.println(mSatSnr);
                } else {
                    mSatSnr = -10;
                }
            }
        };
        mLocationManager.registerGnssStatusCallback(mGnssStatusCallback);
    }



    private void reportLocationUpdate(float snr) {
        mLocationManager.unregisterGnssStatusCallback(mGnssStatusCallback);
        mResult.success(snr);
    }
}