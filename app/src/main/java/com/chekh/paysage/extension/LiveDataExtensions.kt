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

fun <X> LiveData<X>.doNext(body: (X?) -> Unit): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x ->
        body(x)
        result.value = x
    }
    return result
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

fun <X, Y, Z> zip(
    x: LiveData<X>,
    y: LiveData<Y>,
    merge: (x: X?, z: Y?) -> Z?
): LiveData<Z> {
    val mergeLiveData = MediatorLiveData<Z>()
    mergeLiveData.addSource(x) { xValue ->
        val yValue = y.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue)
    }
    mergeLiveData.addSource(y) { yValue ->
        val xValue = x.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue)
    }
    return mergeLiveData
}

fun <X, Y, Z, R> zip(
    x: LiveData<X>,
    y: LiveData<Y>,
    z: LiveData<Z>,
    merge: (x: X, y: Y, z: Z) -> R
): LiveData<R> {
    val mergeLiveData = MediatorLiveData<R>()
    mergeLiveData.addSource(x) { xValue ->
        val yValue = y.value ?: return@addSource
        val zValue = z.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue, zValue)
    }
    mergeLiveData.addSource(y) { yValue ->
        val xValue = x.value ?: return@addSource
        val zValue = z.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue, zValue)
    }
    mergeLiveData.addSource(z) { zValue ->
        val xValue = x.value ?: return@addSource
        val yValue = y.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue, zValue)
    }
    return mergeLiveData
}

fun <X, Y, Z, W, R> zip(
    x: LiveData<X>,
    y: LiveData<Y>,
    z: LiveData<Z>,
    w: LiveData<W>,
    merge: (x: X, y: Y, z: Z, w: W) -> R
): LiveData<R> {
    val mergeLiveData = MediatorLiveData<R>()
    mergeLiveData.addSource(x) { xValue ->
        val yValue = y.value ?: return@addSource
        val zValue = z.value ?: return@addSource
        val wValue = w.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue, zValue, wValue)
    }
    mergeLiveData.addSource(y) { yValue ->
        val xValue = x.value ?: return@addSource
        val zValue = z.value ?: return@addSource
        val wValue = w.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue, zValue, wValue)
    }
    mergeLiveData.addSource(z) { zValue ->
        val xValue = x.value ?: return@addSource
        val yValue = y.value ?: return@addSource
        val wValue = w.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue, zValue, wValue)
    }
    mergeLiveData.addSource(w) { wValue ->
        val xValue = x.value ?: return@addSource
        val yValue = y.value ?: return@addSource
        val zValue = z.value ?: return@addSource
        mergeLiveData.value = merge(xValue, yValue, zValue, wValue)
    }
    return mergeLiveData
}

fun <T> MutableLiveData<T>.recharge() {
    value = value
}

fun <X, R : Comparable<R>> LiveData<List<X>>.sortedBy(selector: (X) -> R?): LiveData<List<X>> {
    return map { it?.sortedBy(selector) }
}

fun <X, Y> LiveData<List<X>>.foreachMap(body: (X) -> Y): LiveData<List<Y>> {
    return map { x -> x?.map { body(it) } }
}