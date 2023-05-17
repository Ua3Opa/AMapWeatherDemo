package com.amapweather.domain

import com.amapweather.entitys.WeatherResponse
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject
import kotlin.reflect.KFunction1

class UseCase @Inject constructor(jobExecutor: JobExecutor) : BaseUseCase<Any>(jobExecutor) {

    private lateinit var observable: (Map<String, String>) -> Observable<WeatherResponse>;
    private lateinit var param: Map<String, String>
    private lateinit var observer: ResultObservable<WeatherResponse>

    fun withFunctional(observable: KFunction1<Map<String, String>, Observable<WeatherResponse>>, param: Map<String, String>): UseCase {
        this.observable = observable
        this.param = param
        return this
    }

    fun withProcessor(observer: ResultObservable<WeatherResponse>): UseCase {
        this.observer = observer
        return this
    }

    override fun buildUseCaseObservable(p: Map<String, String>): Observable<WeatherResponse> {
        return observable.invoke(p);
    }


    fun execute() {
        super.execute(observer, param)
    }

}