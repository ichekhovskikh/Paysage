package com.chekh.paysage.data.service

import android.content.Context
import androidx.lifecycle.LiveData
import com.chekh.paysage.extension.zip
import com.chekh.paysage.feature.main.domain.model.AppSettingsModel
import com.chekh.paysage.tools.SharedPreferenceLiveData
import javax.inject.Inject

interface SettingsService {
    var menuAppsSize: Int
    val menuAppsSizeLiveData: LiveData<Int>

    var menuAppsSpan: Int
    val menuAppsSpanLiveData: LiveData<Int>

    var menuAppSettings: AppSettingsModel
    val menuAppSettingsLiveData: LiveData<AppSettingsModel>

    var dockAppsSize: Int
    val dockAppsSizeLiveData: LiveData<Int>

    var dockAppsSpan: Int
    val dockAppsSpanLiveData: LiveData<Int>

    var dockAppSettings: AppSettingsModel
    val dockAppSettingsLiveData: LiveData<AppSettingsModel>
}

class SettingsServiceImpl @Inject constructor(
    context: Context
) : SettingsService {

    private val pref = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    override var menuAppsSize: Int
        get() = pref.getInt(MENU_APP_SIZE, 240)
        set(value) = pref.edit().putInt(MENU_APP_SIZE, value).apply()

    override val menuAppsSizeLiveData: LiveData<Int> =
        SharedPreferenceLiveData(pref, MENU_APP_SIZE, 240)

    override var menuAppsSpan: Int
        get() = pref.getInt(MENU_APP_SPAN, 4)
        set(value) = pref.edit().putInt(MENU_APP_SPAN, value).apply()

    override val menuAppsSpanLiveData: LiveData<Int> =
        SharedPreferenceLiveData(pref, MENU_APP_SPAN, 4)

    override var menuAppSettings: AppSettingsModel
        get() = AppSettingsModel(menuAppsSize, menuAppsSpan)
        set(value) {
            menuAppsSize = value.appSize
            menuAppsSpan = value.appSpan
        }

    override val menuAppSettingsLiveData: LiveData<AppSettingsModel> =
        zip(menuAppsSizeLiveData, menuAppsSpanLiveData) { size, span ->
            AppSettingsModel(size, span)
        }

    override var dockAppsSize: Int
        get() = pref.getInt(DOCK_APP_SIZE, 240)
        set(value) = pref.edit().putInt(DOCK_APP_SIZE, value).apply()

    override val dockAppsSizeLiveData: LiveData<Int> =
        SharedPreferenceLiveData(pref, DOCK_APP_SIZE, 240)

    override var dockAppsSpan: Int
        get() = pref.getInt(DOCK_APP_SPAN, 4)
        set(value) = pref.edit().putInt(DOCK_APP_SPAN, value).apply()

    override val dockAppsSpanLiveData: LiveData<Int> =
        SharedPreferenceLiveData(pref, DOCK_APP_SPAN, 4)

    override var dockAppSettings: AppSettingsModel
        get() = AppSettingsModel(dockAppsSize, dockAppsSpan)
        set(value) {
            dockAppsSize = value.appSize
            dockAppsSpan = value.appSpan
        }

    override val dockAppSettingsLiveData: LiveData<AppSettingsModel> =
        zip(dockAppsSizeLiveData, dockAppsSpanLiveData) { size, span ->
            AppSettingsModel(size, span)
        }

    companion object {
        private const val SP_NAME = "settings_prefs"
        private const val MENU_APP_SIZE = "menu_app_size"
        private const val DOCK_APP_SIZE = "dock_app_size"
        private const val MENU_APP_SPAN = "menu_app_span"
        private const val DOCK_APP_SPAN = "dock_app_span"
    }
}