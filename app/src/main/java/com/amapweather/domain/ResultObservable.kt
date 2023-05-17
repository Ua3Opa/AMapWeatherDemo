package com.amapweather.domain

import android.util.Log
import io.reactivex.rxjava3.observers.DisposableObserver

class ResultObservable<T> constructor(private var callback: (Any) -> Unit) : DisposableObserver<Any>() {


    override fun onNext(t: Any) {
        callback.invoke(t);
    }

    override fun onError(e: Throwable) {
        e.printStackTrace()
        Log.d("TAG", "onError: ")
    }

    override fun onComplete() {
    }
}