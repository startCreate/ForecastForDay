package com.user.vladimir_voronov_openweathermap;


import com.user.vladimir_voronov_openweathermap.interactors.WeatherInteractor;
import com.user.vladimir_voronov_openweathermap.screens.location.AddLocationPresenter;
import com.user.vladimir_voronov_openweathermap.screens.location.AddLocationView$$State;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Completable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.verify;

public class AddLocationPresenterTest {

    public @Rule MockitoRule rule = MockitoJUnit.rule();
    @Mock private WeatherInteractor interactor;
    private AddLocationPresenter presenter;
    @Mock private AddLocationView$$State viewState;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new AddLocationPresenter(interactor);
        presenter.setViewState(viewState);
    }
    @BeforeClass
    public static void setupClass() {
        RxAndroidPlugins
                .setInitMainThreadSchedulerHandler(schedulerCallable-> Schedulers.trampoline());
    }

    @Test public void onBtnOk_Success() {
        String location = "location";
        assertTrue(!location.trim().isEmpty());

        Mockito.when(interactor.fetchData(location)).thenReturn(Completable.complete());
        presenter.onBtnOK(location);

        verify(viewState).close();
    }

    @Test public void onBtnOk_Failed() {
        String location = "";
        assertFalse(!location.trim().isEmpty());
    }
}
