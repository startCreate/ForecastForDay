package com.user.vladimir_voronov_openweathermap.repositories;


import android.location.Location;

import com.github.popalay.rxrealm.RxRealm;
import com.user.vladimir_voronov_openweathermap.data.WebApi;
import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.ulils.Constants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.realm.Sort;

@Singleton
public class WeatherRepository {

    private WebApi webApi;

    @Inject WeatherRepository(WebApi api) {
        this.webApi = api;
    }

    public Completable fetchData(String location) {
        return webApi.getWeather(location, Constants.UNITS,
                Constants.PERSONAL_KEY_FOR_OPENWEATHERMAP)
                .flatMapCompletable(weatherData -> RxRealm.doTransactional(realm -> {
                    WeatherRealm weatherRealm = new WeatherRealm(weatherData);
                    realm.insertOrUpdate(weatherRealm);
                }));
    }

    public Completable fetchDataByLocation(Location location) {
        return webApi.getWeatherByLocation(location.getLatitude(), location.getLongitude(),
                Constants.UNITS, Constants.PERSONAL_KEY_FOR_OPENWEATHERMAP)
                .flatMapCompletable(weatherData -> RxRealm.doTransactional(realm -> {
                    WeatherRealm weatherRealm = new WeatherRealm(weatherData);
                    weatherRealm.setCurrentLocation(true);
                    realm.where(WeatherRealm.class)
                            .equalTo(Constants.FIELD_CURRENT_LOCATION, true)
                            .findAll().deleteAllFromRealm();
                    realm.insertOrUpdate(weatherRealm);
                }));
    }

    public Completable updateWeather() {
        return RxRealm.getList(realm -> realm.where(WeatherRealm.class).findAll())
                .toFlowable()
                .flatMap(Flowable::fromIterable)
                .flatMap(weatherRealm -> webApi.getWeather(weatherRealm.getCityName(),
                        Constants.UNITS, Constants.PERSONAL_KEY_FOR_OPENWEATHERMAP))
                .flatMapCompletable(weatherData -> RxRealm.doTransactional(realm -> {
                    WeatherRealm currentWeather = realm.where(WeatherRealm.class)
                            .equalTo(Constants.FIELD_CITY_NAME, weatherData.getName()).findFirst();
                    if (!currentWeather.isCurrentLocation()) {
                        realm.insertOrUpdate(new WeatherRealm(weatherData));
                    }
                }));
    }

    public Flowable<List<WeatherRealm>> listenWeather() {
        return RxRealm.listenList(realm -> realm.where(WeatherRealm.class)
                .findAllSorted(Constants.FIELD_CURRENT_LOCATION, Sort.DESCENDING, Constants.FIELD_CITY_NAME, Sort.ASCENDING));
    }

    public Completable remove(WeatherRealm weatherRealm) {
        return RxRealm.doTransactional(realm -> realm.where(WeatherRealm.class)
                .equalTo(Constants.FIELD_CITY_NAME, weatherRealm.getCityName())
                .findFirst().deleteFromRealm());
    }

    public Completable addToDB(WeatherRealm weatherRealm) {
        return RxRealm.doTransactional(realm -> realm.insertOrUpdate(weatherRealm));
    }

    public Maybe<WeatherRealm> getWeather(String location) {
        return RxRealm.getElement(realm -> realm.where(WeatherRealm.class)
                .equalTo(Constants.FIELD_CITY_NAME, location).findFirst());
    }
}
