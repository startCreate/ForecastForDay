package com.user.vladimir_voronov_openweathermap.ulils;

import com.user.vladimir_voronov_openweathermap.R;

import java.util.Date;

public class WeatherUtils {

    private WeatherUtils() {
    }

    public static int getIconForWeather(int idWeather) {
        if (idWeather >= 200 && idWeather <= 232)
            return R.drawable.storm;

        if (idWeather >= 300 && idWeather <= 531)
            return R.drawable.shower_rain;

        if (idWeather >= 600 && idWeather <= 622)
            return R.drawable.snow;

        if (idWeather >= 800 && idWeather <= 801)
            return R.drawable.sunny;

        if (idWeather >= 802 && idWeather <= 804)
            return R.drawable.cloudy;

        return R.drawable.storm;
    }

    public static String convertData(Long unixData) {
        return Constants.DATAFORMAT.format(new Date(unixData * 1000));
    }
}
