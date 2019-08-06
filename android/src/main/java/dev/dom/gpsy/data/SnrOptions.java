package dev.dom.gpsy.data;

import java.util.Map;


public class SnrOptions {

    private int accuracy;

    public static SnrOptions parseArguments(Object arguments) {
        Map<String, Object> map = (Map<String, Object>) arguments;

        final int accuracy = (int) map.get("accuracy");

        return new SnrOptions(accuracy);
    }


    private SnrOptions( int accuracy) {
        this.accuracy = accuracy;
    }


    public int getAccuracy() {
        return accuracy;
    }
}