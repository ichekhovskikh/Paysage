package com.chekh.paysage.core.provider

import android.content.Context
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.mapper.AppSettingsModelMapper
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.extension.zip
import com.chekh.paysage.core.tools.SharedPreferenceLiveData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

interface SettingsProvider {
    var boardAppsSize: Int
    val boardAppsSizeLiveData: LiveData<Int>

    var boardAppsSpan: Int
    val boardAppsSpanLiveData: LiveData<Int>

    var boardAppSettings: AppSettingsModel
    val boardAppSettingsLiveData: LiveData<AppSettingsModel>

    var dockAppsSize: Int
    val dockAppsSizeLiveData: LiveData<Int>

    var dockAppsSpan: Int
    val dockAppsSpanLiveData: LiveData<Int>

    var dockAppSettings: AppSettingsModel
    val dockAppSettingsLiveData: LiveData<AppSettingsModel>

    var desktopGridSpan: Int
    val desktopGridSpanLiveData: LiveData<Int>
}

class SettingsProviderImpl @Inject constructor(
    @ApplicationContext
    context: Context,
    private val appSettingsModelMapper: AppSettingsModelMapper
) : SettingsProvider {

    private val pref = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)

    override var boardAppsSize: Int
        get() = pref.getInt(BOARD_APP_SIZE, 240)
        set(value) = pref.edit().putInt(BOARD_APP_SIZE, value).apply()

    override val boardAppsSizeLiveData: LiveData<Int> =
        SharedPreferenceLiveData(pref, BOARD_APP_SIZE, 240)

    override var boardAppsSpan: Int
        get() = pref.getInt(BOARD_APP_SPAN, 4)
        set(value) = pref.edit().putInt(BOARD_APP_SPAN, value).apply()

    override val boardAppsSpanLiveData: LiveData<Int> =
        SharedPreferenceLiveData(pref, BOARD_APP_SPAN, 4)

    override var boardAppSettings: AppSettingsModel
        get() = appSettingsModelMapper.map(boardAppsSize, boardAppsSpan)
        set(value) {
            boardAppsSize = value.appSize
            boardAppsSpan = value.appSpan
        }

    override val boardAppSettingsLiveData: LiveData<AppSettingsModel> =
        zip(boardAppsSizeLiveData, boardAppsSpanLiveData) { size, span ->
            appSettingsModelMapper.map(size, span)
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
        get() = appSettingsModelMapper.map(dockAppsSize, dockAppsSpan)
        set(value) {
            dockAppsSize = value.appSize
            dockAppsSpan = value.appSpan
        }

    override val dockAppSettingsLiveData: LiveData<AppSettingsModel> =
        zip(dockAppsSizeLiveData, dockAppsSpanLiveData) { size, span ->
            appSettingsModelMapper.map(size, span)
        }

    override var desktopGridSpan: Int
        get() = pref.getInt(DESKTOP_GRID_SPAN, 5)
        set(value) = pref.edit().putInt(DESKTOP_GRID_SPAN, value).apply()

    override val desktopGridSpanLiveData: LiveData<Int> =
        SharedPreferenceLiveData(pref, DESKTOP_GRID_SPAN, 5)

    private companion object {
        const val SP_NAME = "settings_prefs"
        const val BOARD_APP_SIZE = "board_app_size"
        const val DOCK_APP_SIZE = "dock_app_size"
        const val BOARD_APP_SPAN = "board_app_span"
        const val DOCK_APP_SPAN = "dock_app_span"
        const val DESKTOP_GRID_SPAN = "desktop_grid_span"
    }
}
