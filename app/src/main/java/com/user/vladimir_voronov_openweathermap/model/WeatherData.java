package com.user.vladimir_voronov_openweathermap.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherData {

    @SerializedName("coord") private Coord coord;
    @SerializedName("weather") private List<Weather> weather = null;
    @SerializedName("main") private Main main;
    @SerializedName("name") private String name;

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
