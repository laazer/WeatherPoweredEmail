package com.laazer.wpe.dao;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Laazer
 */
public interface IZipCodeAccessor {
    Pattern ZIP_CODE_PATTERN = Pattern.compile("\\d{5}(?:[-\\s]\\d{4})?");

    /**
     * Get a zip code from a given address string.
     * @param address address.
     * @return a zip code from a given address string.
     */
    default String getZipCodeFromString(String address) {

        final Matcher matcher = ZIP_CODE_PATTERN.matcher(address);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * Get the postal code from a given city.
     *
     * @param cityState city.
     * @return the postal code from the given cty.
     */
     String getZipCodeFromCity(final String cityState);
}
