package com.chekh.paysage.feature.main.data.service

import android.content.pm.LauncherActivityInfo
import android.content.pm.LauncherApps
import android.os.UserManager
import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.AppDao
import com.chekh.paysage.common.data.dao.PackageDao
import com.chekh.paysage.common.data.factory.AppSettingsFactory
import com.chekh.paysage.common.data.model.AppCategory.OTHER
import com.chekh.paysage.common.data.model.AppSettingsEntity
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.data.mapper.AppModelMapper
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import javax.inject.Inject

interface AppService {

    val installedApps: LiveData<List<AppModel>>

    suspend fun pullApps(packageName: String? = null)

    suspend fun startObserveAppUpdates(callback: AppsChangedCallback)

    suspend fun stopObserveAppUpdates(callback: AppsChangedCallback)
}

class AppServiceImpl @Inject constructor(
    private val launcherApps: LauncherApps,
    private val userManager: UserManager,
    private val appDao: AppDao,
    private val packageDao: PackageDao,
    private val appSettingsFactory: AppSettingsFactory,
    private val appMapper: AppModelMapper
) : AppService {

    override val installedApps: LiveData<List<AppModel>> = appDao.getAll()
        .foreachMap { appMapper.map(it) }

    override suspend fun startObserveAppUpdates(callback: AppsChangedCallback) {
        launcherApps.registerCallback(callback)
    }

    override suspend fun stopObserveAppUpdates(callback: AppsChangedCallback) {
        launcherApps.unregisterCallback(callback)
    }

    override suspend fun pullApps(packageName: String?) {
        val activityInfos = userManager.userProfiles.flatMap {
            launcherApps.getActivityList(packageName, it)
        }
        if (activityInfos.isEmpty()) {
            removePackage(packageName)
            return
        }
        val cachedAppsSettings = appDao.getAsyncAll()
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

    private suspend fun removePackage(packageName: String?) {
        when (packageName == null) {
            true -> appDao.removeAll()
            else -> appDao.removeByPackageName(packageName)
        }
    }

    private suspend fun pullUpdatedAppSettings(
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

    private suspend fun createAppSettings(
        activityInfo: LauncherActivityInfo,
        appsSettings: List<AppSettingsEntity>
    ): AppSettingsEntity {
        val componentName = activityInfo.componentName
        val categoryId = packageDao.getAsyncCategoryId(componentName.packageName) ?: OTHER.id
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
