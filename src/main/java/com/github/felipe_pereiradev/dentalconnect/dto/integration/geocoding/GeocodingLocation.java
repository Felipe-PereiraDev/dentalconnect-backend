package com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GeocodingLocation {
    private double lat;
    private double lng;
}
