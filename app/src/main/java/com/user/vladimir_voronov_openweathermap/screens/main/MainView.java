package com.user.vladimir_voronov_openweathermap.screens.main;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.screens.base.BaseView;

import java.util.List;

import io.reactivex.disposables.Disposable;

@StateStrategyType(value = AddToEndSingleStrategy.class)
public interface MainView extends BaseView {

    void showData(List<WeatherRealm> weatherRealmList);

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void openAddLocationScreen();

    @StateStrategyType(value = OneExecutionStateStrategy.class)
    void openDetailScreen(String location);

    void stopRefreshingLayout();

    void addDisposable(Disposable disposable);

    void showMessage(String message);
}
