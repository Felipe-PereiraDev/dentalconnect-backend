package com.github.felipe_pereiradev.dentalconnect.integration;

import com.github.felipe_pereiradev.dentalconnect.config.GoogleFeignConfig;
import com.github.felipe_pereiradev.dentalconnect.dto.integration.geocoding.GeocodingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "google-geocoding",
        url = "https://maps.googleapis.com",
        configuration = GoogleFeignConfig.class
)
public interface GoogleGeocodingClient {

    @GetMapping(value = "/maps/api/geocode/json")
    GeocodingResponse getGeocodingByZipCode(@RequestParam("address") String cep);
}
