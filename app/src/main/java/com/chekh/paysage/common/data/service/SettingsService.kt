package com.chekh.paysage.common.data.service

import android.content.SharedPreferences
import com.chekh.paysage.common.data.mapper.AppSettingsModelMapper
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.extension.getAsFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

interface SettingsService {
    val boardAppsSize: Flow<Int>
    suspend fun setBoardAppsSize(value: Int)

    val boardAppsSpan: Flow<Int>
    suspend fun setBoardAppsSpan(value: Int)

    val boardAppSettings: Flow<AppSettingsModel>
    suspend fun setBoardAppSettings(value: AppSettingsModel)

    val dockAppsSize: Flow<Int>
    suspend fun setDockAppsSize(value: Int)

    val dockAppsSpan: Flow<Int>
    suspend fun setDockAppsSpan(value: Int)

    val dockAppSettings: Flow<AppSettingsModel>
    suspend fun setDockAppSettings(value: AppSettingsModel)

    val desktopGridSpan: Flow<Int>
    suspend fun setDesktopGridSpan(value: Int)
}

@ExperimentalCoroutinesApi
class SettingsServiceImpl @Inject constructor(
    private val preferences: SharedPreferences,
    private val appSettingsModelMapper: AppSettingsModelMapper
) : SettingsService {

    override val boardAppsSize: Flow<Int> = preferences.getAsFlow(BOARD_APP_SIZE, 240)

    override suspend fun setBoardAppsSize(value: Int) {
        preferences.edit().putInt(BOARD_APP_SIZE, value).apply()
    }

    override val boardAppsSpan: Flow<Int> = preferences.getAsFlow(BOARD_APP_SPAN, 4)

    override suspend fun setBoardAppsSpan(value: Int) {
        preferences.edit().putInt(BOARD_APP_SPAN, value).apply()
    }

    override val boardAppSettings: Flow<AppSettingsModel> =
        boardAppsSize.zip(boardAppsSpan) { size, span ->
            appSettingsModelMapper.map(size, span)
        }

    override suspend fun setBoardAppSettings(value: AppSettingsModel) {
        setBoardAppsSize(value.appSize)
        setBoardAppsSpan(value.appSpan)
    }

    override val dockAppsSize: Flow<Int> = preferences.getAsFlow(DOCK_APP_SIZE, 240)

    override suspend fun setDockAppsSize(value: Int) {
        preferences.edit().putInt(DOCK_APP_SIZE, value).apply()
    }

    override val dockAppsSpan: Flow<Int> = preferences.getAsFlow(DOCK_APP_SPAN, 4)

    override suspend fun setDockAppsSpan(value: Int) {
        preferences.edit().putInt(DOCK_APP_SPAN, value).apply()
    }

    override val dockAppSettings: Flow<AppSettingsModel> =
        dockAppsSize.zip(dockAppsSpan) { size, span ->
            appSettingsModelMapper.map(size, span)
        }

    override suspend fun setDockAppSettings(value: AppSettingsModel) {
        setDockAppsSize(value.appSize)
        setDockAppsSpan(value.appSpan)
    }

    override val desktopGridSpan: Flow<Int> = preferences.getAsFlow(DESKTOP_GRID_SPAN, 5)

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
