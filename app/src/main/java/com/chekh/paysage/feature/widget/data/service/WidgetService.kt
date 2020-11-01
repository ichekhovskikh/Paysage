package com.chekh.paysage.feature.widget.data.service

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProviderInfo
import android.content.pm.LauncherApps
import android.os.UserHandle
import android.os.UserManager
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import com.chekh.paysage.feature.widget.data.mapper.WidgetAppModelMapper
import com.chekh.paysage.feature.widget.data.mapper.WidgetModelMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetAppModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface WidgetService {

    val installedWidgetsLiveData: LiveData<List<WidgetModel>>

    fun getFirstWidgetAppByPackage(packageName: String): LiveData<WidgetAppModel>
}

class WidgetServiceImpl @Inject constructor(
    launcherApps: LauncherApps,
    dispatcherProvider: DispatcherProvider,
    widgetManager: AppWidgetManager,
    userManager: UserManager,
    private val appDao: AppDao,
    private val widgetMapper: WidgetModelMapper,
    private val widgetAppMapper: WidgetAppModelMapper
) : WidgetService {

    override val installedWidgetsLiveData: LiveData<List<WidgetModel>> =
        WidgetListLiveData(launcherApps, dispatcherProvider, widgetManager, userManager)
            .foreachMap { widgetMapper.map(it) }

    override fun getFirstWidgetAppByPackage(packageName: String) =
        appDao.getFirstByPackageNameLive(packageName)
            .map { app -> app?.let { widgetAppMapper.map(it) } }
}

private class WidgetListLiveData(
    private val launcherApps: LauncherApps,
    private val dispatcherProvider: DispatcherProvider,
    private val widgetManager: AppWidgetManager,
    private val userManager: UserManager
) : LiveData<List<AppWidgetProviderInfo>>() {

    private val appsChangedCallback = object : AppsChangedCallback() {

        override fun onAppsChanged(packageName: String, userHandle: UserHandle) {
            pullWidgetsAsync()
        }
    }

    init {
        pullWidgetsAsync()
    }

    private fun pullWidgetsAsync() {
        CoroutineScope(dispatcherProvider.ui).launch {
            value = withContext(CoroutineScope(dispatcherProvider.io).coroutineContext) {
                getWidgets()
            }
        }
    }

    private fun getWidgets(): List<AppWidgetProviderInfo> =
        mutableListOf<AppWidgetProviderInfo>().apply {
            userManager.userProfiles.forEach {
                addAll(widgetManager.getInstalledProvidersForProfile(it))
            }
        }

    override fun onActive() {
        super.onActive()
        launcherApps.registerCallback(appsChangedCallback)
    }

    override fun onInactive() {
        launcherApps.unregisterCallback(appsChangedCallback)
        super.onInactive()
    }
}
