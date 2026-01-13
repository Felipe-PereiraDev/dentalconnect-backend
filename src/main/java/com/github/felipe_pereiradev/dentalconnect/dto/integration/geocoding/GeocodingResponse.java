package com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding;

import java.util.List;

public record GeocodingResponse(
        List<GeocodingResult> results
) {
}
