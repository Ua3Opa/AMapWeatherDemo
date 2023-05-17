package com.amapweather.service

import com.amapweather.entitys.WeatherResponse
import com.amapweather.entitys.Forecast
import com.amapweather.entitys.Lives
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface AMapService {
    @GET("/v3/weather/weatherInfo?")
    fun getWeatherInfo(@QueryMap map: Map<String,String>): Observable<WeatherResponse>
}