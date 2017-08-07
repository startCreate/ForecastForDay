package com.user.vladimir_voronov_openweathermap.ulils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Constants {
    public static final String BASE_URL = "http://api.openweathermap.org/";
    public static final String PERSONAL_KEY_FOR_OPENWEATHERMAP = "f2bcac60b649c39d1ec3c45c19d126ca";
    public static final String UNITS = "metric";
    public static final String FIELD_CURRENT_LOCATION = "isCurrentLocation";
    public static final String FIELD_CITY_NAME = "cityName";
    public static final String REALM_NAME = "Weather.realm";
    public static final long UPDATE_LOCATION = 10 * 1000;
    public static final float MIN_DISTANCE = 1000;
    public static final String KEY_LOCATION = "location";
    public static SimpleDateFormat DATAFORMAT = getDataFormat();

    private static SimpleDateFormat getDataFormat() {
        if (DATAFORMAT == null)
            return new SimpleDateFormat("EEEE, d MMMM ''yy", Locale.US);
        return DATAFORMAT;
    }
}
