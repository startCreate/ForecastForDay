package com.user.vladimir_voronov_openweathermap.data;

import com.user.vladimir_voronov_openweathermap.model.WeatherData;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebApi {

    @GET("/data/2.5/weather") Flowable<WeatherData> getWeather(@Query("q") String location,
                                                               @Query("units") String units,
                                                               @Query("APPID") String appId);

    @GET("/data/2.5/weather") Flowable<WeatherData> getWeatherByLocation(@Query("lat") Double latitude,
                                                                         @Query("lon") Double longitude,
                                                                         @Query("units") String units,
                                                                         @Query("APPID") String appId);
}
