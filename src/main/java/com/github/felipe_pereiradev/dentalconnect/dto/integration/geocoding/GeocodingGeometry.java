package com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding;

public record GeocodingGeometry (
        GeocodingLocation location
){
    public double getLatitude() {
        return location.getLat();
    }

    public double getLongitude() {
        return location.getLng();
    }
}
