package com.amapweather.model

import com.amapweather.entitys.WeatherResponse
import com.amapweather.service.ServiceManager
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class AMapRepository @Inject constructor(var serviceManager: ServiceManager) {

    fun getWeatherInfo(params: Map<String, String>): Observable<WeatherResponse> {
        return serviceManager.aMapService.getWeatherInfo(params)
    }

}