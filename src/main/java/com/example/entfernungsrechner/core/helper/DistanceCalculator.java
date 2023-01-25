package com.example.entfernungsrechner.core.helper;

public class DistanceCalculator {
    private static final double R = 6372.8;

    /**
     * @param lat1 Latitude of the first location
     * @param lon1 Longitude of the first location
     * @param lat2 Latitude of the second location
     * @param lon2 Longitude of the second location
     * @return rounded distance in kilometres
     */
    public static Long calcDistanceInKM(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(dLat / 2), 2) + Math.pow(Math.sin(dLon / 2), 2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return Math.round(R * c);
    }

}
