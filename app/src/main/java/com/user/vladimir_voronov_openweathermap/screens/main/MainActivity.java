package com.user.vladimir_voronov_openweathermap.screens.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.user.vladimir_voronov_openweathermap.R;
import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.screens.adapter.MyItemDecorator;
import com.user.vladimir_voronov_openweathermap.screens.adapter.WeatherAdapter;
import com.user.vladimir_voronov_openweathermap.screens.adapter.WeatherAdapterOnClick;
import com.user.vladimir_voronov_openweathermap.screens.detail.WeatherDetailActivity;
import com.user.vladimir_voronov_openweathermap.screens.location.AddLocationActivity;
import com.user.vladimir_voronov_openweathermap.ulils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends MvpAppCompatActivity
        implements MainView, ItemTouchHelperAdapter, WeatherAdapterOnClick {

    public WeatherAdapter weatherAdapter;
    CompositeDisposable composite = new CompositeDisposable();
    @BindView(R.id.recyclerviewForecast) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;
    @Inject
    @InjectPresenter MainPresenter mainPresenter;
    private ItemTouchHelper itemTouchHelper;
    private RxPermissions rxPermissions;
    private LocationManager locationManager;
    private Drawable drawable;
    private LocationListener locationListener = new LocationListener() {
        @SuppressLint("MissingPermission") @Override public void onLocationChanged(Location location) {
            mainPresenter.showMyLocation(location);
        }

        @SuppressLint("MissingPermission") @Override public void onStatusChanged(String s, int i, Bundle bundle) {
            mainPresenter.showMyLocation(locationManager.getLastKnownLocation(s));
        }

        @SuppressLint("MissingPermission") @Override public void onProviderEnabled(String s) {
        }

        @Override public void onProviderDisabled(String s) {
        }
    };

    @ProvidePresenter public MainPresenter getMainPresenter() {
        return mainPresenter;
    }

    @SuppressLint("MissingPermission") @Override protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        initPermission();
        init();
    }

    @Override protected void onDestroy() {
        composite.clear();
        locationManager.removeUpdates(locationListener);
        super.onDestroy();
    }

    @Override public void openAddLocationScreen() {
        startActivity(AddLocationActivity.getIntent(this));
    }

    @Override public void openDetailScreen(String location) {
        startActivity(WeatherDetailActivity.getIntent(this, location));
    }

    @Override public void showError(String error) {
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override public void showData(List<WeatherRealm> weatherRealmList) {
        weatherAdapter.setData(weatherRealmList);
    }

    @Override public void stopRefreshingLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override public void addDisposable(Disposable disposable) {
        composite.add(disposable);
    }

    @Override public void showMessage(String message) {
        Snackbar.make(recyclerView, getString(R.string.show_deleteItem, message), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.undoAction), view -> mainPresenter.addItemOnUndo()).show();
    }

    @Override public void onSwipe(RecyclerView.ViewHolder viewHolder) {
        WeatherRealm currentWeather = weatherAdapter.getData().get(viewHolder.getAdapterPosition());
        mainPresenter.onItemSwiped(currentWeather);
    }

    @Override public void onClick(String location) {
        openDetailScreen(location);
    }

    private void init() {
        weatherAdapter = new WeatherAdapter(this);
        recyclerView.setAdapter(weatherAdapter);
        recyclerView.addItemDecoration(new MyItemDecorator());
        fab.setOnClickListener(view -> openAddLocationScreen());
        drawable = ContextCompat.getDrawable(this, R.drawable.ic_delete_sweep_grey600_24dp);
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchHelperCallback(this, drawable));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.refreshData());
    }

    @SuppressLint("MissingPermission") private void initPermission() {
        rxPermissions = new RxPermissions(this);
        addDisposable(rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                Constants.UPDATE_LOCATION, Constants.MIN_DISTANCE, locationListener);
                    } else
                        showError(getString(R.string.permission_denied));
                }));
    }
}
