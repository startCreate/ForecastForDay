package com.user.vladimir_voronov_openweathermap;


import com.user.vladimir_voronov_openweathermap.interactors.WeatherInteractor;
import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.repositories.WeatherRepository;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WeatherInteractorTest {

    @Rule public MockitoRule rule = MockitoJUnit.rule();
    @Mock private WeatherRepository repository;
    @InjectMocks private WeatherInteractor interactor;

    @Test public void listenWeather_Success() {
        WeatherRealm weatherKiev = new WeatherRealm();
        WeatherRealm weatherKharkov = new WeatherRealm();

        when(repository.listenWeather())
                .thenReturn(Flowable.fromArray(Arrays.asList(weatherKiev, weatherKharkov)));

        TestSubscriber<List<WeatherRealm>> testObserver = interactor.listenWeather().test();

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors()
                .assertValue(weatherRealms -> weatherRealms.contains(weatherKiev) && weatherRealms.contains(weatherKharkov))
                .assertComplete();

        verify(repository).listenWeather();
    }

    @Test public void listenWeather_Empty() {
        when(repository.listenWeather()).thenReturn(Flowable.fromArray(new ArrayList<>()));

        TestSubscriber<List<WeatherRealm>> testObserver = interactor.listenWeather().test();

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors()
                .assertValue(List::isEmpty)
                .assertComplete();

        verify(repository).listenWeather();
    }

    @Test public void fetchWeather_Success() {
        when(repository.fetchData("Kiev")).thenReturn(Completable.complete());

        TestObserver<Void> testObserver = interactor.fetchData("Kiev").test();

        testObserver.awaitTerminalEvent();
        testObserver.assertNoErrors()
                .assertComplete();

        verify(repository).fetchData("Kiev");
    }
}
