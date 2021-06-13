package com.chekh.paysage.common.data.datasource

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.mapper.AppSettingsModelMapper
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.core.extension.zip
import com.chekh.paysage.core.tools.SharedPreferenceLiveData
import com.chekh.paysage.core.ui.tools.Size
import com.chekh.paysage.core.ui.tools.on
import javax.inject.Inject

interface SettingsDataSource {
    val boardAppsSize: LiveData<Int>
    suspend fun setBoardAppsSize(value: Int)

    val boardAppColumns: LiveData<Int>
    suspend fun setBoardAppColumns(value: Int)

    val boardAppSettings: LiveData<AppSettingsModel>
    suspend fun setBoardAppSettings(value: AppSettingsModel)

    val dockAppsSize: LiveData<Int>
    suspend fun setDockAppsSize(value: Int)

    val dockAppColumns: LiveData<Int>
    suspend fun setDockAppColumns(value: Int)

    val dockAppSettings: LiveData<AppSettingsModel>
    suspend fun setDockAppSettings(value: AppSettingsModel)

    val desktopGridSize: LiveData<Size>
    suspend fun setDesktopGridSize(value: Size)
}

class SettingsDataSourceImpl @Inject constructor(
    private val preferences: SharedPreferences,
    private val appSettingsModelMapper: AppSettingsModelMapper
) : SettingsDataSource {

    override val boardAppsSize: LiveData<Int> =
        SharedPreferenceLiveData(preferences, BOARD_APP_SIZE, DEFAULT_BOARD_APP_SIZE)

    override suspend fun setBoardAppsSize(value: Int) {
        preferences.edit().putInt(BOARD_APP_SIZE, value).apply()
    }

    override val boardAppColumns: LiveData<Int> =
        SharedPreferenceLiveData(preferences, BOARD_APP_COLUMNS, DEFAULT_BOARD_APP_COLUMNS)

    override suspend fun setBoardAppColumns(value: Int) {
        preferences.edit().putInt(BOARD_APP_COLUMNS, value).apply()
    }

    override val boardAppSettings: LiveData<AppSettingsModel> =
        zip(boardAppsSize, boardAppColumns) { size, columnCount ->
            appSettingsModelMapper.map(size, columnCount)
        }

    override suspend fun setBoardAppSettings(value: AppSettingsModel) {
        setBoardAppsSize(value.appSize)
        setBoardAppColumns(value.appColumnCount)
    }

    override val dockAppsSize: LiveData<Int> =
        SharedPreferenceLiveData(preferences, DOCK_APP_SIZE, DEFAULT_DOCK_APP_SIZE)

    override suspend fun setDockAppsSize(value: Int) {
        preferences.edit().putInt(DOCK_APP_SIZE, value).apply()
    }

    override val dockAppColumns: LiveData<Int> =
        SharedPreferenceLiveData(preferences, DOCK_APP_COLUMNS, DEFAULT_DOCK_APP_COLUMNS)

    override suspend fun setDockAppColumns(value: Int) {
        preferences.edit().putInt(DOCK_APP_COLUMNS, value).apply()
    }

    override val dockAppSettings: LiveData<AppSettingsModel> =
        zip(dockAppsSize, dockAppColumns) { size, columnCount ->
            appSettingsModelMapper.map(size, columnCount)
        }

    override suspend fun setDockAppSettings(value: AppSettingsModel) {
        setDockAppsSize(value.appSize)
        setDockAppColumns(value.appColumnCount)
    }

    override val desktopGridSize: LiveData<Size> =
        SharedPreferenceLiveData(preferences, DESKTOP_GRID_SIZE, -1L)
            .map {
                if (it == null || it < 0) {
                    DEFAULT_DESKTOP_GRID_SIZE
                } else {
                    it.toInt() on (it shr 32).toInt()
                }
            }

    override suspend fun setDesktopGridSize(value: Size) {
        val size = value.width.toLong() + (value.height.toLong() shl 32)
        preferences.edit().putLong(DESKTOP_GRID_SIZE, size).apply()
    }

    private companion object {
        const val BOARD_APP_SIZE = "board_app_size"
        const val DOCK_APP_SIZE = "dock_app_size"
        const val BOARD_APP_COLUMNS = "board_app_columns"
        const val DOCK_APP_COLUMNS = "dock_app_columns"
        const val DESKTOP_GRID_SIZE = "desktop_grid_size"

        const val DEFAULT_BOARD_APP_SIZE = 240
        const val DEFAULT_DOCK_APP_SIZE = 240
        const val DEFAULT_BOARD_APP_COLUMNS = 4
        const val DEFAULT_DOCK_APP_COLUMNS = 4
        val DEFAULT_DESKTOP_GRID_SIZE = 5 on 6
    }
}
