package com.github.felipe_pereiradev.dentalconnect.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class GeoDistanceUtils {

    public static double calculateRadiusKm(double clinicLat, double clinicLon, double userLat, double userLon) {
        final double EARTH_RADIUS_KM = 6371.0;

        double latDistance = Math.toRadians(userLat - clinicLat);
        double lonDistance = Math.toRadians(userLon - clinicLon);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(clinicLat))
                * Math.cos(Math.toRadians(userLat))
                * Math.sin(lonDistance / 2)
                * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS_KM * c;
        return  BigDecimal.valueOf(distance)
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }

}
