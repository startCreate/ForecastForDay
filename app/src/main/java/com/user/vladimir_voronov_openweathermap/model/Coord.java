package com.user.vladimir_voronov_openweathermap.model;

import com.google.gson.annotations.SerializedName;

public class Coord {

    @SerializedName("longitude") private Double longitude;
    @SerializedName("latitude") private Double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
