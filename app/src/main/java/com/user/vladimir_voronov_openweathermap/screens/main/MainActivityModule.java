package com.user.vladimir_voronov_openweathermap.screens.main;


import com.user.vladimir_voronov_openweathermap.interactors.WeatherInteractor;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides MainPresenter providePresenter(WeatherInteractor interactor) {
        return new MainPresenter(interactor);
    }
}
