package com.user.vladimir_voronov_openweathermap.screens.main;

import android.location.Location;

import com.arellomobile.mvp.InjectViewState;
import com.user.vladimir_voronov_openweathermap.interactors.WeatherInteractor;
import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.screens.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class MainPresenter extends BasePresenter<MainView> {

    private WeatherInteractor interactor;
    private WeatherRealm currentItemForDelete;

    @Inject public MainPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
        addDisposable(this.interactor.listenWeather()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getViewState()::showData,
                        this::handleError));
        refreshData();
    }

    private WeatherRealm getCurrentItemForDelete() {
        return currentItemForDelete;
    }

    private void setCurrentItemForDelete(WeatherRealm currentItemForDelete) {
        this.currentItemForDelete = currentItemForDelete;
    }

    void addItemOnUndo() {
        addDisposable(interactor.addOnUndo(getCurrentItemForDelete())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());
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

    void onItemSwiped(WeatherRealm weatherRealm) {
        setCurrentItemForDelete(weatherRealm);
        addDisposable(interactor.remove(weatherRealm)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().showMessage(weatherRealm.getCityName()),
                        this::handleError));
    }

    private void handleError(Throwable t) {
        getViewState().showError(t.getMessage());
    }
}
