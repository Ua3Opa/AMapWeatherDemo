package com.amapweather.module

import com.amapweather.config.Api.Companion.CONNECT_TIME_OUT
import com.amapweather.http.RequestIntercept
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RetrofitModule {

    @Singleton
    @Provides
    fun provideIntercept(intercept: RequestIntercept): Interceptor {
        return intercept
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Singleton
    @Provides
    fun provideClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
    }

    @Provides
    fun provideOkHttpClient(okHttpClient: OkHttpClient.Builder, intercept: Interceptor?): OkHttpClient {
        val builder: OkHttpClient.Builder = okHttpClient.retryOnConnectionFailure(false).connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS).readTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
        builder.addInterceptor(intercept!!)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(builder: Retrofit.Builder, client: OkHttpClient, httpUrl: HttpUrl): Retrofit {
        return builder.baseUrl(httpUrl).client(client).addCallAdapterFactory(RxJava3CallAdapterFactory.create()) //使用rxjava
            .addConverterFactory(GsonConverterFactory.create()) //使用Gson
            .build()
    }
}