package com.user.vladimir_voronov_openweathermap.injection;


import com.user.vladimir_voronov_openweathermap.screens.location.AddLocationActivity;
import com.user.vladimir_voronov_openweathermap.screens.location.LocationActivityModule;
import com.user.vladimir_voronov_openweathermap.screens.main.MainActivity;
import com.user.vladimir_voronov_openweathermap.screens.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
abstract class BuilderModule {
    @ContributesAndroidInjector(modules = MainActivityModule.class)
    abstract MainActivity bindMainActivityInjectorFactory();

    @ContributesAndroidInjector(modules = LocationActivityModule.class)
    abstract AddLocationActivity bindLocationActivityInjectorFactory();


}
