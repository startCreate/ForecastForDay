package com.user.vladimir_voronov_openweathermap.screens.location;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.user.vladimir_voronov_openweathermap.screens.base.BaseView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AddLocationView extends BaseView {

    @StateStrategyType(OneExecutionStateStrategy.class)
    void close();
}
