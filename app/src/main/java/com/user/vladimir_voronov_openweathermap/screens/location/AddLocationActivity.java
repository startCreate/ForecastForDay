package com.user.vladimir_voronov_openweathermap.screens.location;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.user.vladimir_voronov_openweathermap.R;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dagger.android.AndroidInjection;

public class AddLocationActivity extends MvpAppCompatActivity implements AddLocationView {

    private static final String TAG = AddLocationActivity.class.getSimpleName();

    @BindView(R.id.inputLocation) EditText inputLocation;

    @Inject
    @InjectPresenter AddLocationPresenter locationPresenter;

    public static Intent getIntent(Context context) {
        return new Intent(context, AddLocationActivity.class);
    }

    @ProvidePresenter public AddLocationPresenter getLocationPresenter() {
        return locationPresenter;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.add_location);
        ButterKnife.bind(this);
    }

    @Override public void showError(String error) {
        Snackbar snack = Snackbar.make(inputLocation, error, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();
    }

    @Override public void close() {
        finish();
    }

    @OnClick(R.id.btnCancel) void onClickCancel() {
        close();
    }

    @OnClick(R.id.btnOk) void onClickOK() {
        locationPresenter.onBtnOK(inputLocation.getText().toString());
    }
}

