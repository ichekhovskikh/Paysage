package com.chekh.paysage.feature.widget.data.datasource

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.pm.LauncherApps
import android.os.UserManager
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.feature.main.tools.onAppsChanged
import com.chekh.paysage.feature.widget.data.mapper.AppForWidgetModelMapper
import com.chekh.paysage.feature.widget.data.mapper.WidgetModelMapper
import com.chekh.paysage.feature.widget.domain.model.AppForWidgetModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import javax.inject.Inject

interface WidgetDataSource {

    val installedWidgets: LiveData<List<WidgetModel>>

    fun getFirstAppForWidgetPackage(packageName: String): LiveData<AppForWidgetModel>
}

class WidgetDataSourceImpl @Inject constructor(
    launcherApps: LauncherApps,
    widgetManager: AppWidgetManager,
    userManager: UserManager,
    private val appDao: AppDao,
    private val widgetMapper: WidgetModelMapper,
    private val appForWidgetMapper: AppForWidgetModelMapper
) : WidgetDataSource {

    override val installedWidgets =
        WidgetProvidersLiveData(launcherApps, widgetManager, userManager)
            .foreachMap(widgetMapper::map)

    override fun getFirstAppForWidgetPackage(packageName: String) =
        appDao.getFirstByPackageName(packageName)
            .map { app -> app?.let { appForWidgetMapper.map(it) } }
}

private class WidgetProvidersLiveData(
    private val launcherApps: LauncherApps,
    private val widgetManager: AppWidgetManager,
    private val userManager: UserManager
) : LiveData<List<AppWidgetProviderInfo>>() {

    private val callback = onAppsChanged { _, _ -> value = getWidgets() }

    init {
        value = getWidgets()
    }

    private fun getWidgets() = userManager.userProfiles.flatMap {
        widgetManager.getInstalledProvidersForProfile(it)
    }

    override fun onActive() {
        super.onActive()
        launcherApps.registerCallback(callback)
    }

    override fun onInactive() {
        launcherApps.unregisterCallback(callback)
        super.onInactive()
    }
}
