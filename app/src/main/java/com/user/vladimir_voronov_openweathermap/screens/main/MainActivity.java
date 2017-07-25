package com.user.vladimir_voronov_openweathermap.screens.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.user.vladimir_voronov_openweathermap.R;
import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.screens.adapter.MyItemDecorator;
import com.user.vladimir_voronov_openweathermap.screens.adapter.WeatherAdapter;
import com.user.vladimir_voronov_openweathermap.screens.location.AddLocationActivity;
import com.user.vladimir_voronov_openweathermap.ulils.Constants;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.AndroidInjection;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends MvpAppCompatActivity implements MainView {

    public WeatherAdapter weatherAdapter;
    CompositeDisposable composite = new CompositeDisposable();
    @BindView(R.id.recyclerviewForecast) RecyclerView recyclerView;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.swipeRefreshLayout) SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    @InjectPresenter MainPresenter mainPresenter;

    private final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.
            SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
        @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                        RecyclerView.ViewHolder target) {
            return false;
        }

        @Override public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return viewHolder.getAdapterPosition() == 0 ? 0 : makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            String location = (String) viewHolder.itemView.getTag();
            mainPresenter.removeItem(location);
        }
    });

    private RxPermissions rxPermissions;
    private LocationManager locationManager;
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

    @SuppressLint("MissingPermission") private void initPermission() {
        rxPermissions = new RxPermissions(this);
        addDisposable(rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) {
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                                Constants.UPDATE_LOCATION, Constants.MIN_DISTANCE, locationListener);
                    } else
                        showError("Permission denied");
                }));
    }

    @Override public void openAddLocationScreen() {
        startActivity(AddLocationActivity.getIntent(this));
    }

    @Override public void showError(String error) {
        Log.d("TEST", error);
        Snackbar.make(recyclerView, error, Snackbar.LENGTH_LONG).show();
    }

    @Override public void showData(List<WeatherRealm> weatherRealmList) {
        weatherAdapter.setData(weatherRealmList);
    }

    @Override public void stopRefreshingLayout() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void init() {
        weatherAdapter = new WeatherAdapter();
        recyclerView.setAdapter(weatherAdapter);
        recyclerView.addItemDecoration(new MyItemDecorator());
        fab.setOnClickListener(view -> openAddLocationScreen());
        itemTouchHelper.attachToRecyclerView(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(() -> mainPresenter.refreshData());
    }

    @Override public void addDisposable(Disposable disposable) {
        composite.add(disposable);
    }
}
