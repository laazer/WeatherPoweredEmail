package com.laazer.wpe.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Laazer
 */
@Slf4j
public class ZipCodeAccessor implements IZipCodeAccessor {

    private final String apiKey;
    private final static String PATH_FORMAT = "http://www.zipcodeapi.com/rest/{0}/city-zips.json/{1}/{2}";
    private final static String DELIMITER = ", ";

    public ZipCodeAccessor(final String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public String getZipCodeFromCity(final String cityState) {
        final String zipCode = getZipCodeFromString(cityState);
        if (zipCode != null) {
            return zipCode;
        }
        final RestTemplate restTemplate = new RestTemplate();
        final ZipCodesWrapper wrapper = restTemplate.getForObject(this.getPath(cityState),
                ZipCodesWrapper.class);
        return wrapper.zip_codes != null && wrapper.zip_codes.length != 0 ? wrapper.zip_codes[0]
                : null;
    }

    private String getPath(final String cityState) {
        final String[] parts = cityState.split(DELIMITER);
        final String city = parts[0];
        final String state = parts[1];
        final String path = MessageFormat.format(PATH_FORMAT, this.apiKey, city, state);
        log.info(path);
        return path;
    }

    /**
     * Wrapper class for responses from the zip code api;
     */
    @Data
    @NoArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class ZipCodesWrapper {
        private String[] zip_codes;
    }
}
