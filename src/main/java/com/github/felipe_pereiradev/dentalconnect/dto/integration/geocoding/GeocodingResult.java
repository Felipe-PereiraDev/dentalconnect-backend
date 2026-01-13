package com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding;

public record GeocodingResult(
        GeocodingGeometry geometry
) {

    public double getLatitude() {
        return geometry.getLatitude();
    }

    public double getLongitude() {
        return geometry.getLongitude();
    }
}
