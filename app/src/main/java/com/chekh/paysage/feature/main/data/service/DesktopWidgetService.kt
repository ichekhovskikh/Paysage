package com.chekh.paysage.feature.main.data.service

import android.appwidget.AppWidgetHost
import android.appwidget.AppWidgetManager
import android.os.UserManager
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.DesktopWidgetDao
import com.chekh.paysage.common.data.model.DesktopWidgetSettingsEntity
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.feature.main.data.mapper.DesktopWidgetModelMapper
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface DesktopWidgetService {

    val desktopWidgets: LiveData<List<DesktopWidgetModel>>

    suspend fun startObserveWidgetEvents()

    suspend fun stopObserveWidgetEvents()

    suspend fun updateDesktopWidget(widget: DesktopWidgetModel)

    suspend fun updateAllDesktopWidgets(widgets: List<DesktopWidgetModel>?)

    suspend fun removeDesktopWidget(widgetId: String)

    suspend fun pullWidgets()
}

class DesktopWidgetServiceImpl @Inject constructor(
    private val widgetManager: AppWidgetManager,
    private val userManager: UserManager,
    private val widgetHost: AppWidgetHost,
    private val dispatcherProvider: DispatcherProvider,
    private val desktopWidgetDao: DesktopWidgetDao,
    private val desktopWidgetMapper: DesktopWidgetModelMapper
) : DesktopWidgetService {

    override val desktopWidgets: LiveData<List<DesktopWidgetModel>> = desktopWidgetDao.getAll()
        .map { it?.filterInstalled() }
        .foreachMap { desktopWidgetMapper.map(it) }

    override suspend fun startObserveWidgetEvents() {
        widgetHost.startListening()
    }

    override suspend fun stopObserveWidgetEvents() {
        widgetHost.stopListening()
    }

    override suspend fun updateDesktopWidget(widget: DesktopWidgetModel) {
        desktopWidgetDao.update(desktopWidgetMapper.unmap(widget))
    }

    override suspend fun updateAllDesktopWidgets(widgets: List<DesktopWidgetModel>?) {
        if (widgets == null) {
            desktopWidgetDao.removeAll()
            return
        }
        val newWidgets = widgets.map { desktopWidgetMapper.unmap(it) }
        desktopWidgetDao.updateAll(newWidgets)
    }

    override suspend fun removeDesktopWidget(widgetId: String) {
        desktopWidgetDao.removeById(widgetId)
    }

    override suspend fun pullWidgets() {
        val desktopWidgets = desktopWidgetDao.getAsyncAll()
        val installedDesktopWidgets = withContext(dispatcherProvider.main) {
            desktopWidgets.filterInstalled()
        }
        desktopWidgetDao.updateAll(installedDesktopWidgets)
    }

    private fun List<DesktopWidgetSettingsEntity>.filterInstalled(): List<DesktopWidgetSettingsEntity> {
        val installedWidgets = userManager.userProfiles.flatMap {
            widgetManager.getInstalledProvidersForProfile(it)
        }
        return filter { desktopWidget ->
            installedWidgets.any { installedWidget ->
                val provider = installedWidget.provider
                desktopWidget.packageName == provider.packageName && desktopWidget.className == provider.className
            }
        }
    }
}
