package com.user.vladimir_voronov_openweathermap.screens.location;

import com.arellomobile.mvp.InjectViewState;
import com.user.vladimir_voronov_openweathermap.interactors.WeatherInteractor;
import com.user.vladimir_voronov_openweathermap.screens.base.BasePresenter;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;

@InjectViewState
public class AddLocationPresenter extends BasePresenter<AddLocationView> {

    private final WeatherInteractor interactor;

    @Inject public AddLocationPresenter(WeatherInteractor interactor) {
        this.interactor = interactor;
    }


    public void onBtnOK(String location) {
        if (location.trim().length() == 0) {
            getViewState().showError("Input Location");
            return;
        }
        addDisposable(interactor.fetchData(location)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> getViewState().close(), this::handleError));
    }

    private void handleError(Throwable t) {
        getViewState().showError("An error occurred during networking: " + t.getMessage());
    }
}
