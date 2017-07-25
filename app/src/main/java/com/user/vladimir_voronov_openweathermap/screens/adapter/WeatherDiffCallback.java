package com.user.vladimir_voronov_openweathermap.screens.adapter;


import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;

import java.util.List;

public class WeatherDiffCallback extends DiffUtil.Callback {

    private final List<WeatherRealm> oldData;
    private final List<WeatherRealm> newData;

    WeatherDiffCallback(List<WeatherRealm> oldList, List<WeatherRealm> newList) {
        this.oldData = oldList;
        this.newData = newList;
    }

    @Override public int getOldListSize() {
        return oldData.size();
    }

    @Override public int getNewListSize() {
        return newData.size();
    }

    @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).getCityName().equals(newData.get(newItemPosition).getCityName());
    }

    @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldData.get(oldItemPosition).equals(newData.get(newItemPosition));
    }

    @Nullable @Override public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
