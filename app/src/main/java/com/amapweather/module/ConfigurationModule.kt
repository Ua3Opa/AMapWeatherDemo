package com.amapweather.module

import com.amapweather.config.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConfigurationModule {
    @Provides
    open fun provideHttpUrl(): HttpUrl {
        return Api.HOST_AMAP.toHttpUrlOrNull()!!
    }

}