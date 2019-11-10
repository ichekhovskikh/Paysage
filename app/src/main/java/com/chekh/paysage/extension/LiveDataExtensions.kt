package com.chekh.paysage.extension

import androidx.lifecycle.*

fun <T> LiveData<T>.observe(owner: LifecycleOwner, callback: (data: T) -> Unit) {
    observe(owner, Observer<T> { data ->
        data?.let { callback.invoke(it) }
    })
}

fun <T> LiveData<T>.observeOnce(observer: Observer<T>) {
    observeForever(OnceObserver(this, observer))
}

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, OnceObserver(this, observer))
}

private class OnceObserver<T>(val liveData: LiveData<T>, val observer: Observer<T>) : Observer<T> {
    override fun onChanged(data: T?) {
        observer.onChanged(data)
        liveData.removeObserver(this)
    }
}

fun <X, Y> LiveData<X>.map(body: (X?) -> Y?): LiveData<Y> {
    return Transformations.map(this, body)
}

fun <X, Y> LiveData<X>.switchMap(body: (X?) -> LiveData<Y>): LiveData<Y> {
    return Transformations.switchMap(this, body)
}

fun <X> LiveData<X>.filter(condition: (X?) -> Boolean): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x -> if (condition(x)) result.value = x }
    return result
}

fun <X, Y, Z> zip(x: LiveData<X>, z: LiveData<Z>, merge: (x: X?, z: Z?) -> Y?): LiveData<Y> {
    val mergeLiveData = MediatorLiveData<Y>()
    mergeLiveData.addSource(x) { xValue ->
        mergeLiveData.value = merge(xValue, z.value)
    }
    mergeLiveData.addSource(z) { zValue ->
        mergeLiveData.value = merge(x.value, zValue)
    }
    return mergeLiveData
}

fun <T> MutableLiveData<T>.recharge() {
    value = value
}