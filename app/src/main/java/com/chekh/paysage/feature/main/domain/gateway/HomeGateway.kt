package com.chekh.paysage.feature.main.domain.gateway

import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.model.CategoryModel
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import kotlinx.coroutines.flow.Flow

interface HomeGateway {
    suspend fun startObserveWidgetEvents()
    suspend fun stopObserveWidgetEvents()

    suspend fun startObserveAppUpdates(callback: AppsChangedCallback)
    suspend fun stopObserveAppUpdates(callback: AppsChangedCallback)

    fun getDockApps(): Flow<List<AppModel>>
    fun getDockAppSettings(): Flow<AppSettingsModel>

    fun getAppCategories(): Flow<List<CategoryModel>>

    fun getBoardApps(): Flow<List<AppModel>>
    fun getBoardAppSettings(): Flow<AppSettingsModel>

    suspend fun pullBoardApps(packageName: String)
    suspend fun pullDesktopWidgets(packageName: String)
}
