package com.user.vladimir_voronov_openweathermap.screens.base;


import com.arellomobile.mvp.MvpView;

public interface BaseView extends MvpView {

    void showError(String error);
}
