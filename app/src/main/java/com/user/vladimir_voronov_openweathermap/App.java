package com.user.vladimir_voronov_openweathermap;


import com.user.vladimir_voronov_openweathermap.injection.DaggerAppComponent;

import javax.inject.Singleton;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import io.realm.Realm;

public class App extends DaggerApplication {

    @Override public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }

    @Singleton
    @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}



