package com.chekh.paysage.feature.widget.data.service

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.pm.LauncherApps
import android.os.UserManager
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import com.chekh.paysage.feature.widget.data.mapper.WidgetAppModelMapper
import com.chekh.paysage.feature.widget.data.mapper.WidgetModelMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetAppModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

interface WidgetService {

    val installedWidgets: Flow<List<WidgetModel>>

    suspend fun getFirstAppForWidgetPackage(packageName: String): WidgetAppModel

    suspend fun pullWidgets()

    suspend fun startObserveWidgetUpdates(callback: AppsChangedCallback)

    suspend fun stopObserveWidgetUpdates(callback: AppsChangedCallback)
}

@ExperimentalCoroutinesApi
class WidgetServiceImpl @Inject constructor(
    private val launcherApps: LauncherApps,
    private val widgetManager: AppWidgetManager,
    private val userManager: UserManager,
    private val appDao: AppDao,
    private val widgetMapper: WidgetModelMapper,
    private val widgetAppMapper: WidgetAppModelMapper
) : WidgetService {

    private val mutableInstalledWidgets = MutableStateFlow(getWidgets())

    override val installedWidgets = mutableInstalledWidgets
        .foreachMap { widgetMapper.map(it) }

    override suspend fun getFirstAppForWidgetPackage(packageName: String) =
        widgetAppMapper.map(appDao.getFirstByPackageName(packageName))

    override suspend fun startObserveWidgetUpdates(callback: AppsChangedCallback) {
        launcherApps.registerCallback(callback)
    }

    override suspend fun stopObserveWidgetUpdates(callback: AppsChangedCallback) {
        launcherApps.unregisterCallback(callback)
    }

    override suspend fun pullWidgets() {
        mutableInstalledWidgets.value = getWidgets()
    }

    private fun getWidgets(): List<AppWidgetProviderInfo> =
        userManager.userProfiles.flatMap {
            widgetManager.getInstalledProvidersForProfile(it)
        }
}
