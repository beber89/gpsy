package dev.dom.gpsy.data;

import java.util.Map;


public class SnrOptions {

    private int accuracy;
    private long distanceFilter;
    private boolean forceAndroidLocationManager;
    private long timeInterval;

    public static SnrOptions parseArguments(Object arguments) {
        Map<String, Object> map = (Map<String, Object>) arguments;

        final int accuracy = (int) map.get("accuracy");
        final long distanceFilter = (int) map.get("distanceFilter");
        final boolean forceAndroidLocationManager = (boolean) map.get("forceAndroidLocationManager");
        final long timeInterval = (int) map.get("timeInterval");

        return new SnrOptions(accuracy, distanceFilter, forceAndroidLocationManager, timeInterval);
    }


    private SnrOptions( int accuracy, long distanceFilter, boolean forceAndroidLocationManager, long timeInterval) {
        this.accuracy = accuracy;
        this.distanceFilter = distanceFilter;
        this.forceAndroidLocationManager = forceAndroidLocationManager;
        this.timeInterval = timeInterval;
    }


    public int getAccuracy() {
        return accuracy;
    }

    public long getDistanceFilter() {
        return distanceFilter;
    }

    public boolean isForceAndroidLocationManager() {
        return forceAndroidLocationManager;
    }

    public long getTimeInterval() {
        return timeInterval;
    }
}