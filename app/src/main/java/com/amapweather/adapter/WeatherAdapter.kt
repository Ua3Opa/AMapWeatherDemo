package com.amapweather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amapweather.databinding.ItemCityBinding
import com.amapweather.databinding.ItemWeatherBinding
import com.amapweather.entitys.CityData
import com.amapweather.entitys.Forecast
import com.amapweather.viewmodel.MainViewModel

class WeatherAdapter(var context: Context, var mainViewModel: MainViewModel, var listener: (CityData) -> Unit) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemWeatherBinding.inflate(LayoutInflater.from(context)))
    }

    override fun getItemCount(): Int {
        return if (mainViewModel.forecastData.value == null) {
            0
        } else {
            mainViewModel.forecastData.value!!.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mainViewModel.forecastData.value == null) {
            return
        }

        val data = mainViewModel.forecastData.value!!.get(position)

        holder.binding.tvDate.text = "日期 ${data.date}"
        holder.binding.tvWeather.text = "天气 ${data.dayweather}"
        holder.binding.tvWeatherNight.text = "夜间天气 ${data.nightweather}"
        holder.binding.tvTemp.text = "温度 ${data.daytemp}"
        holder.binding.tvWind.text = "风向 ${data.daywind}"
    }

    class ViewHolder(var binding: ItemWeatherBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}