package com.user.vladimir_voronov_openweathermap.model;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("id") private Integer id;
    @SerializedName("main") private String main;
    @SerializedName("description") private String description;
    @SerializedName("icon") private String icon;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }
}
