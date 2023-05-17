package com.amapweather.service

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ServiceManager @Inject constructor(var aMapService: AMapService) {
}