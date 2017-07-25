package com.user.vladimir_voronov_openweathermap.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class WeatherRealm extends RealmObject {

    @PrimaryKey
    @Required
    private String cityName;
    private Integer weatherId;
    private Double temp;
    private Boolean isCurrentLocation;
    public WeatherRealm() {
    }

    public WeatherRealm(WeatherData weatherData) {
        setCityName(weatherData.getName());
        setWeatherId(weatherData.getWeather().get(0).getId());
        setTemp(weatherData.getMain().getTemp());
        setCurrentLocation(false);
    }

    public String getCityName() {
        return cityName;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public Double getTemp() {
        return temp;
    }

    public Boolean isCurrentLocation() {
        return isCurrentLocation;
    }

    public void setCurrentLocation(Boolean currentLocation) {
        isCurrentLocation = currentLocation;
    }

    private void setCityName(String cityName) {
        this.cityName = cityName;
    }

    private void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    private void setTemp(Double temp) {
        this.temp = temp;
    }
}
