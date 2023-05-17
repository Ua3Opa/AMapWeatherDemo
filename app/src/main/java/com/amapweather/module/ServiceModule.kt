package com.amapweather.module

import com.amapweather.service.AMapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Singleton
    @Provides
    fun provideAMapService(retrofit: Retrofit): AMapService {
        return retrofit.create(AMapService::class.java)
    }

}