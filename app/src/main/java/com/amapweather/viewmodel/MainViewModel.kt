package com.amapweather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import com.amapweather.domain.ResultObservable
import com.amapweather.domain.UseCase
import com.amapweather.entitys.CityData
import com.amapweather.entitys.Forecast
import com.amapweather.entitys.WeatherResponse
import com.amapweather.mapper.MainParamsMapper
import com.amapweather.model.AMapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    @Inject
    lateinit var aMapRepository: AMapRepository

    @Inject
    lateinit var mainParamsMapper: MainParamsMapper

    @Inject
    lateinit var useCase: UseCase;

    public var forecastData = MutableLiveData<List<Forecast>>();

    fun queryWeather(city: CityData) {
        useCase.withFunctional(aMapRepository::getWeatherInfo, mainParamsMapper.buildAMapQueryParams(city.cityCode))
            .withProcessor(ResultObservable {
                Log.d("TAG", "queryWeather: ${JSON.toJSON(it)}")
                forecastData.value = (it as WeatherResponse).forecasts?.get(0)?.casts
            }).execute()
    }

}