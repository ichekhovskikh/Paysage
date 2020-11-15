package com.chekh.paysage.core.extension

import android.content.SharedPreferences
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import java.lang.IllegalArgumentException

@ExperimentalCoroutinesApi
fun <T> SharedPreferences.getAsFlow(key: String, defValue: T): Flow<T> = callbackFlow {
    val listener = SharedPreferences.OnSharedPreferenceChangeListener { _, changedByKey ->
        if (changedByKey == key) {
            offer(getValue(key, defValue))
        }
    }
    registerOnSharedPreferenceChangeListener(listener)
    awaitClose { unregisterOnSharedPreferenceChangeListener(listener) }
}
    .onStart { emit(getValue(key, defValue)) }

@Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
fun <T> SharedPreferences.getValue(key: String, defValue: T): T = when (defValue) {
    is Int -> getInt(key, defValue)
    is Long -> getLong(key, defValue)
    is Boolean -> getBoolean(key, defValue)
    is Float -> getFloat(key, defValue)
    is String -> getString(key, defValue)
    else -> throw IllegalArgumentException("Unsupported value Class")
} as T
