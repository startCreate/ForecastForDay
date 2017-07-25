package com.user.vladimir_voronov_openweathermap.injection;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.user.vladimir_voronov_openweathermap.data.WebApi;
import com.user.vladimir_voronov_openweathermap.ulils.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class AppModule {

    @Provides
    @Singleton Realm provideRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .name(Constants.REALM_NAME)
                .schemaVersion(0).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(configuration);
        return Realm.getInstance(configuration);
    }

    @Provides
    @Singleton Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build();
    }

    @Provides
    @Singleton WebApi provideApi(Retrofit retrofit) {
        return retrofit.create(WebApi.class);
    }


}
