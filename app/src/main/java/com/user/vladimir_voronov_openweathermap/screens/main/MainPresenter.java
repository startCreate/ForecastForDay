package com.user.vladimir_voronov_openweathermap.screens.main;

import android.location.Location;

import com.arellomobile.mvp.InjectViewState;
import com.user.vladimir_voronov_openweathermap.interactors.WeatherInteractor;
import com.user.vladimir_voronov_openweathermap.screens.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private WeatherInteractor interactor;

    @Inject public MainPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
        addDisposable(this.interactor.listenWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showData,
                        this::handleError));
        refreshData();
    }

    void removeItem(String location) {
        addDisposable(interactor.remove(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, this::handleError));
    }

    void showMyLocation(Location location) {
        addDisposable(interactor.fetchDataByLocation(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                }, this::handleError));
    }

    void refreshData() {
        addDisposable(interactor.updateWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::stopRefreshingLayout,
                        this::handleError));
    }

    private void handleError(Throwable t) {
        getViewState().showError(t.getMessage());
    }
}
