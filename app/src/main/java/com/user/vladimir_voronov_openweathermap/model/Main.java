package com.user.vladimir_voronov_openweathermap.model;

import com.google.gson.annotations.SerializedName;

public class Main {

    @SerializedName("temp") private Double temp;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }
}
