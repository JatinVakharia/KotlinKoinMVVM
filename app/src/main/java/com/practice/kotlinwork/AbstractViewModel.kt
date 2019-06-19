package com.practice.kotlinwork

import android.arch.lifecycle.ViewModel
import android.support.annotation.CallSuper
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


abstract class AbstractViewModel : ViewModel() {
    val disposable = CompositeDisposable()

    fun launch(job : () -> Disposable){
        disposable.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}