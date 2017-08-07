package com.user.vladimir_voronov_openweathermap.interactors;


import android.location.Location;

import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.repositories.WeatherRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class WeatherInteractor {

    private WeatherRepository repository;

    @Inject public WeatherInteractor(WeatherRepository repository) {
        this.repository = repository;
    }

    public Completable fetchData(String location) {
        return repository.fetchData(location)
                .subscribeOn(Schedulers.io());
    }

    public Completable fetchDataByLocation(Location location) {
        return repository.fetchDataByLocation(location)
                .subscribeOn(Schedulers.io());
    }

    public Completable updateWeather() {
        return repository.updateWeather()
                .subscribeOn(Schedulers.io());
    }

    public Flowable<List<WeatherRealm>> listenWeather() {
        return repository.listenWeather().subscribeOn(Schedulers.io());
    }

    public Completable addOnUndo(WeatherRealm weatherRealm) {
        return repository.addToDB(weatherRealm);
    }

    public Completable remove(WeatherRealm weatherRealm) {
        return repository.remove(weatherRealm).subscribeOn(Schedulers.io());
    }

    public Maybe<WeatherRealm> getWeather(String location) {
        return repository.getWeather(location).subscribeOn(Schedulers.io());
    }
}
