package com.chekh.paysage.tools

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import java.lang.IllegalArgumentException

class SharedPreferenceLiveData<T>(
    private val sharedPrefs: SharedPreferences,
    private val key: String,
    private val defValue: T
) : LiveData<T>() {

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            if (key == this.key) {
                value = getValueFromPreferences(sharedPrefs, key, defValue)
            }
        }

    init {
        value = getValueFromPreferences(sharedPrefs, key, defValue)
    }

    @Suppress("UNCHECKED_CAST", "IMPLICIT_CAST_TO_ANY")
    private fun getValueFromPreferences(
        sharedPrefs: SharedPreferences,
        key: String,
        defValue: T
    ): T = when (defValue) {
        is Int -> sharedPrefs.getInt(key, defValue)
        is Long -> sharedPrefs.getLong(key, defValue)
        is Boolean -> sharedPrefs.getBoolean(key, defValue)
        is Float -> sharedPrefs.getFloat(key, defValue)
        else -> throw IllegalArgumentException("Unsupported value Class")
    } as T

    override fun onActive() {
        super.onActive()
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }
}