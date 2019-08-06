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


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Map;



public class SnrUpdatesUsingLocationManagerTask extends Task<SnrOptions> {
    private final Context mAndroidContext;
    final SnrOptions mSnrOptions;
    private float mSatSnr;
    LocationManager mLocationManager;
    GnssStatus.Callback mGnssStatusCallback;


    SnrUpdatesUsingLocationManagerTask(TaskContext<SnrOptions> context) {
        super(context);

        PluginRegistry.Registrar registrar = context.getRegistrar();
        mAndroidContext = registrar.activity() != null ? registrar.activity() : registrar.activeContext();
        mSnrOptions = context.getOptions();
        atStart();
    }

    @Override
    public void startTask() {
        reportLocationUpdate(mSatSnr);
    }

    @Override
    public void stopTask() {
        mLocationManager.unregisterGnssStatusCallback(
                mGnssStatusCallback
        );
        super.stopTask();
    }

    private void handleError() {
        getTaskContext().getResult().error(
                "INVALID_LOCATION_SETTINGS",
                "Location settings are inadequate, check your location settings.",
                null);
    }


    private void atStart() {
        mLocationManager = (LocationManager) mAndroidContext.getSystemService(Context.LOCATION_SERVICE);
        mGnssStatusCallback = new GnssStatus.Callback() {
            @Override
            public void onSatelliteStatusChanged(GnssStatus status) {
                System.out.println("Callback");
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

        getTaskContext().getResult().success(snr);
    }
}