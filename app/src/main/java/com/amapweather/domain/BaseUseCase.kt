package com.amapweather.domain

import com.amapweather.entitys.WeatherResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseUseCase<T : Any> constructor(var threadExecutor: JobExecutor) {

    abstract fun buildUseCaseObservable(p: Map<String,String>): Observable<WeatherResponse>

    protected open fun execute(observer: ResultObservable<WeatherResponse>, params: Map<String,String>) {
        val observable: Observable<WeatherResponse> = buildUseCaseObservable(params)
            .subscribeOn(Schedulers.from(threadExecutor))
            .observeOn(AndroidSchedulers.mainThread())
        observable.subscribeWith(observer)
    }

}