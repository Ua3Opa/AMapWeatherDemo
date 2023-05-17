package com.amapweather.mapper

import com.amapweather.activity.MainActivity.Companion.KEY
import javax.inject.Inject

class MainParamsMapper @Inject constructor() {

    fun buildAMapQueryParams(city: String): Map<String, String> {
        var params = HashMap<String, String>()
        params.put("key",KEY)
        params.put("city",city)
        params.put("extensions","all")
        params.put("output","json")
        return params
    }

}