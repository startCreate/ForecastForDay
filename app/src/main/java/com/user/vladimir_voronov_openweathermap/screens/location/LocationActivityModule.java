package com.user.vladimir_voronov_openweathermap.screens.location;

import com.user.vladimir_voronov_openweathermap.interactors.WeatherInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class LocationActivityModule {

    @Provides AddLocationPresenter provideAddLocationPresenter(WeatherInteractor interactor) {
        return new AddLocationPresenter(interactor);
    }
}
