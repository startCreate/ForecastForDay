package com.user.vladimir_voronov_openweathermap.screens.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.user.vladimir_voronov_openweathermap.R;
import com.user.vladimir_voronov_openweathermap.model.WeatherRealm;
import com.user.vladimir_voronov_openweathermap.ulils.WeatherUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.support.v7.widget.RecyclerView.ViewHolder;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherAdapterViewHolder> {

    private List<WeatherRealm> data = new ArrayList<>();

    public void setData(List<WeatherRealm> newData) {
        final WeatherDiffCallback diffCallback = new WeatherDiffCallback(this.data, newData);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.data.clear();
        this.data.addAll(newData);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override public WeatherAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new WeatherAdapterViewHolder(view);
    }

    @Override public void onBindViewHolder(WeatherAdapterViewHolder holder, int position) {
        WeatherRealm weatherToday = data.get(position);

        holder.bind(weatherToday, position);
    }

    @Override public int getItemCount() {
        return data.size();
    }

    static class WeatherAdapterViewHolder extends ViewHolder {

        @BindView(R.id.location) TextView weatherLocation;
        @BindView(R.id.temperature) TextView weatherTemp;
        @BindView(R.id.weatherIcon) ImageView weatherId;

        WeatherAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(WeatherRealm weatherToday, int position) {
            weatherId.setImageResource(WeatherUtils.getIconForWeather(weatherToday
                    .getWeatherId()));
            weatherLocation.setText(weatherToday.getCityName());
            weatherTemp.setText(itemView.getContext().getString(R.string.temperature_template,
                    weatherToday.getTemp().intValue()));
            itemView.setTag(weatherToday.getCityName());
        }
    }
}
