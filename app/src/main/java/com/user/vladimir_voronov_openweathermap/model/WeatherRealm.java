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
    private Double pressure;
    private Double humidity;
    private Double tempMin;
    private Double tempMax;
    private String main;
    private Long data;

    public WeatherRealm() {
    }

    public WeatherRealm(WeatherData weatherData) {
        setCityName(weatherData.getName());
        setWeatherId(weatherData.getWeather().get(0).getId());
        setTemp(weatherData.getMain().getTemp());
        setHumidity(weatherData.getMain().getHumidity());
        setPressure(weatherData.getMain().getPressure());
        setTempMax(weatherData.getMain().getTempMax());
        setTempMin(weatherData.getMain().getTempMin());
        setMain(weatherData.getWeather().get(0).getMain());
        setData(weatherData.getData());
        setCurrentLocation(false);
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public String getCityName() {
        return cityName;
    }

    private void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    private void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public Double getTemp() {
        return temp;
    }

    private void setTemp(Double temp) {
        this.temp = temp;
    }

    public Boolean isCurrentLocation() {
        return isCurrentLocation;
    }

    public void setCurrentLocation(Boolean currentLocation) {
        isCurrentLocation = currentLocation;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public Long getData() {
        return data;
    }

    public void setData(Long data) {
        this.data = data;
    }
}
