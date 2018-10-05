package com.laazer.wpe.dao;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;

import java.io.IOException;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
public class GoogleMapsAccessor implements IZipCodeAccessor {

    private final GeoApiContext geoContext;

    public GoogleMapsAccessor(final GeoApiContext geoContext) {
        this.geoContext = geoContext;
    }

    @Override
    public String getZipCodeFromCity(final String cityState) {
        final String zipCode = getZipCodeFromString(cityState);
        if (zipCode != null) {
            return zipCode;
        }
        try {
            log.info(cityState);
            final GeocodingResult[] results = GeocodingApi.geocode(geoContext, cityState).await();
            log.info(Arrays.toString(results));
            final String postalCode = results.length > 0
                    && results[0].postcodeLocalities != null
                    && results[0].postcodeLocalities.length > 0 ?
                    results[0].postcodeLocalities[0] : null;
            return postalCode;
        } catch (final ApiException | IOException | InterruptedException e) {
            log.error("Exception while retrieving results", e);
        }
        return null;
    }
}
