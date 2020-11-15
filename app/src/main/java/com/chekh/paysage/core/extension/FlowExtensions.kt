package com.chekh.paysage.core.extension

import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.*
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

private const val DEFAULT_TIMEOUT = 5000L

fun <T> Flow<T>.asConflateLiveData(
    context: CoroutineContext = EmptyCoroutineContext,
    timeoutInMs: Long = DEFAULT_TIMEOUT
) = distinctUntilChanged().conflate().asLiveData(context, timeoutInMs)

fun <X, Y> Flow<List<X>>.foreachMap(transform: suspend (X) -> Y): Flow<List<Y>> =
    map { x -> x.map { transform(it) } }

fun <X, Y, Z, R> Flow<X>.zip(
    second: Flow<Y>,
    third: Flow<Z>,
    transform: suspend (X, Y, Z) -> R
): Flow<R> = this
    .zip(second) { x, y -> x to y }
    .zip(third) { (x, y), z -> transform(x, y, z) }

fun <X, R : Comparable<R>> Flow<List<X>>.sortedBy(selector: (X) -> R?): Flow<List<X>> =
    map { it.sortedBy(selector) }
