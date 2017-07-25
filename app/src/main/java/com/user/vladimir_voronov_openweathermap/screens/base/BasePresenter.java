package com.user.vladimir_voronov_openweathermap.screens.base;


import com.arellomobile.mvp.MvpPresenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePresenter<T extends BaseView> extends MvpPresenter<T> {

    CompositeDisposable composite = new CompositeDisposable();

    @Override public void onDestroy() {
        super.onDestroy();
        composite.clear();
    }

    protected void addDisposable(Disposable disposable) {
        composite.add(disposable);
    }

}
