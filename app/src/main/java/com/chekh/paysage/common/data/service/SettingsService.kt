package com.chekh.paysage.common.data.service

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.mapper.AppSettingsModelMapper
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.extension.zip
import com.chekh.paysage.core.tools.SharedPreferenceLiveData
import javax.inject.Inject

interface SettingsService {
    val boardAppsSize: LiveData<Int>
    suspend fun setBoardAppsSize(value: Int)

    val boardAppsSpan: LiveData<Int>
    suspend fun setBoardAppsSpan(value: Int)

    val boardAppSettings: LiveData<AppSettingsModel>
    suspend fun setBoardAppSettings(value: AppSettingsModel)

    val dockAppsSize: LiveData<Int>
    suspend fun setDockAppsSize(value: Int)

    val dockAppsSpan: LiveData<Int>
    suspend fun setDockAppsSpan(value: Int)

    val dockAppSettings: LiveData<AppSettingsModel>
    suspend fun setDockAppSettings(value: AppSettingsModel)

    val desktopGridSpan: LiveData<Int>
    suspend fun setDesktopGridSpan(value: Int)
}

class SettingsServiceImpl @Inject constructor(
    private val preferences: SharedPreferences,
    private val appSettingsModelMapper: AppSettingsModelMapper
) : SettingsService {

    override val boardAppsSize: LiveData<Int> =
        SharedPreferenceLiveData(preferences, BOARD_APP_SIZE, 240)

    override suspend fun setBoardAppsSize(value: Int) {
        preferences.edit().putInt(BOARD_APP_SIZE, value).apply()
    }

    override val boardAppsSpan: LiveData<Int> =
        SharedPreferenceLiveData(preferences, BOARD_APP_SPAN, 4)

    override suspend fun setBoardAppsSpan(value: Int) {
        preferences.edit().putInt(BOARD_APP_SPAN, value).apply()
    }

    override val boardAppSettings: LiveData<AppSettingsModel> =
        zip(boardAppsSize, boardAppsSpan) { size, span ->
            appSettingsModelMapper.map(size, span)
        }

    override suspend fun setBoardAppSettings(value: AppSettingsModel) {
        setBoardAppsSize(value.appSize)
        setBoardAppsSpan(value.appSpan)
    }

    override val dockAppsSize: LiveData<Int> =
        SharedPreferenceLiveData(preferences, DOCK_APP_SIZE, 240)

    override suspend fun setDockAppsSize(value: Int) {
        preferences.edit().putInt(DOCK_APP_SIZE, value).apply()
    }

    override val dockAppsSpan: LiveData<Int> =
        SharedPreferenceLiveData(preferences, DOCK_APP_SPAN, 4)

    override suspend fun setDockAppsSpan(value: Int) {
        preferences.edit().putInt(DOCK_APP_SPAN, value).apply()
    }

    override val dockAppSettings: LiveData<AppSettingsModel> =
        zip(dockAppsSize, dockAppsSpan) { size, span ->
            appSettingsModelMapper.map(size, span)
        }

    override suspend fun setDockAppSettings(value: AppSettingsModel) {
        setDockAppsSize(value.appSize)
        setDockAppsSpan(value.appSpan)
    }

    override val desktopGridSpan: LiveData<Int> =
        SharedPreferenceLiveData(preferences, DESKTOP_GRID_SPAN, 5)

    override suspend fun setDesktopGridSpan(value: Int) {
        preferences.edit().putInt(DESKTOP_GRID_SPAN, value).apply()
    }

    private companion object {
        const val BOARD_APP_SIZE = "board_app_size"
        const val DOCK_APP_SIZE = "dock_app_size"
        const val BOARD_APP_SPAN = "board_app_span"
        const val DOCK_APP_SPAN = "dock_app_span"
        const val DESKTOP_GRID_SPAN = "desktop_grid_span"
    }
}
