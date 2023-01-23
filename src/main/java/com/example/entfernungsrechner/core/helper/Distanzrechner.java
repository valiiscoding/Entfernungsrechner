package com.example.entfernungsrechner.core.helper;

public class Distanzrechner {
    private static final double R = 6372.8;

    public static Long berechneDistanzInKM(Double lat1, Double lon1, Double lat2, Double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return Math.round(R * c);
    }

}
