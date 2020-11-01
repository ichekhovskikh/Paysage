package com.chekh.paysage.feature.main.data.service

import android.content.pm.LauncherActivityInfo
import android.content.pm.LauncherApps
import android.os.Process
import android.os.UserHandle
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.common.data.dao.PackageDao
import com.chekh.paysage.common.data.model.AppCategory.OTHER
import com.chekh.paysage.common.data.model.AppSettingsEntity
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.feature.main.data.factory.AppSettingsFactory
import com.chekh.paysage.feature.main.data.mapper.AppModelMapper
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

interface AppService {

    val installedAppsLiveData: LiveData<List<AppModel>>

    fun startObserveUpdates()

    fun stopObserveUpdates()
}

class AppServiceImpl @Inject constructor(
    private val launcherApps: LauncherApps,
    private val dispatcherProvider: DispatcherProvider,
    private val appDao: AppDao,
    private val packageDao: PackageDao,
    private val appSettingsFactory: AppSettingsFactory,
    private val appMapper: AppModelMapper
) : AppService, AppsChangedCallback() {

    override val installedAppsLiveData: LiveData<List<AppModel>> = appDao.getAllLive()
        .foreachMap { appMapper.map(it) }

    init {
        pullAppsAsync()
    }

    override fun startObserveUpdates() {
        launcherApps.registerCallback(this)
    }

    override fun stopObserveUpdates() {
        launcherApps.unregisterCallback(this)
    }

    override fun onAppsChanged(packageName: String, userHandle: UserHandle) {
        pullAppsAsync(packageName)
    }

    private fun pullAppsAsync(packageName: String? = null) {
        CoroutineScope(dispatcherProvider.io).launch {
            pullApps(packageName)
        }
    }

    private fun pullApps(packageName: String?) {
        val activityInfos = launcherApps.getActivityList(packageName, Process.myUserHandle())
        if (activityInfos.isEmpty()) {
            removePackage(packageName)
            return
        }
        val cachedAppsSettings = appDao.getAll()
        val newAppsSettings = when (packageName == null) {
            true -> mutableSetOf()
            else -> cachedAppsSettings.toMutableSet()
        }
        activityInfos.forEach { activityInfo ->
            val appSettings = pullUpdatedAppSettings(activityInfo, cachedAppsSettings)
            newAppsSettings.update(appSettings)
        }
        appDao.updateAll(newAppsSettings)
    }

    private fun removePackage(packageName: String?) {
        when (packageName == null) {
            true -> appDao.removeAll()
            else -> appDao.removeByPackageName(packageName)
        }
    }

    private fun pullUpdatedAppSettings(
        activityInfo: LauncherActivityInfo,
        appsSettings: List<AppSettingsEntity>
    ): AppSettingsEntity {
        val appSettings = appsSettings.find { it.isSame(activityInfo) }
        return when (appSettings == null) {
            true -> createAppSettings(activityInfo, appsSettings)
            else -> {
                appSettingsFactory.create(
                    activityInfo,
                    appSettings.categoryId,
                    appSettings.boardPosition,
                    appSettings.dockPosition,
                    appSettings.isHidden
                )
            }
        }
    }

    private fun createAppSettings(
        activityInfo: LauncherActivityInfo,
        appsSettings: List<AppSettingsEntity>
    ): AppSettingsEntity {
        val componentName = activityInfo.componentName
        val categoryId = packageDao.getCategoryId(componentName.packageName) ?: OTHER.id
        var boardPosition = -1
        appsSettings.forEach {
            if (it.categoryId == categoryId && it.boardPosition > boardPosition) {
                boardPosition = it.boardPosition
            }
        }
        return appSettingsFactory.create(activityInfo, categoryId, boardPosition + 1)
    }

    private fun AppSettingsEntity.isSame(activityInfo: LauncherActivityInfo): Boolean {
        val componentName = activityInfo.componentName
        return packageName == componentName.packageName && className == componentName.className
    }

    private fun MutableSet<AppSettingsEntity>.update(element: AppSettingsEntity) {
        removeAll { it.id == element.id }
        add(element)
    }
}
