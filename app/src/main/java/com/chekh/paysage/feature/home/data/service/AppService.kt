package com.chekh.paysage.feature.home.data.service

import android.content.pm.LauncherActivityInfo
import android.content.pm.LauncherApps
import android.os.Process
import android.os.UserHandle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.db.dao.AppDao
import com.chekh.paysage.db.dao.PackageDao
import com.chekh.paysage.feature.home.data.factory.AppSettingsFactory
import com.chekh.paysage.feature.home.data.model.AppSettingsModel
import com.chekh.paysage.feature.home.data.model.AppCategory.OTHER
import com.chekh.paysage.feature.home.domain.mapper.AppModelMapper
import com.chekh.paysage.feature.home.domain.model.AppModel
import com.chekh.paysage.feature.home.tools.AppsChangedCallback
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

interface AppService {

    val appsLiveData: LiveData<List<AppModel>>

    fun startObserveUpdates()

    fun stopObserveUpdates()
}

class AppServiceImpl @Inject constructor(
    private val launcherApps: LauncherApps,
    private val appDao: AppDao,
    private val packageDao: PackageDao,
    private val appSettingsFactory: AppSettingsFactory,
    private val appMapper: AppModelMapper
) : AppService, AppsChangedCallback() {

    private val appsMutableLiveData = MutableLiveData<List<AppModel>>()
    override val appsLiveData: LiveData<List<AppModel>> = appsMutableLiveData

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
        GlobalScope.launch { pullApps(packageName) }
    }

    private fun pullApps(packageName: String?) {
        val activityInfos = launcherApps.getActivityList(packageName, Process.myUserHandle())
        if (activityInfos.isEmpty()) {
            removePackage(packageName)
            return
        }

        val apps = appsMutableLiveData.value?.toMutableList() ?: mutableListOf()
        val appsSettings = appDao.getAll().toMutableSet()

        activityInfos.forEach { activityInfo ->
            val appSettings = appsSettings
                .find { isSame(activityInfo, it) }
                ?: createAppSettings(activityInfo, appsSettings)
            appsSettings.add(appSettings)

            val appModel = appMapper.map(activityInfo, appSettings)
            apps.update(appModel)
        }
        appsMutableLiveData.postValue(apps)
        appDao.add(appsSettings)
    }

    private fun removePackage(packageName: String?) {
        packageName ?: return
        val apps = appsMutableLiveData.value?.filter { it.packageName != packageName }
        apps?.let {
            appsMutableLiveData.postValue(it)
            appDao.removeByPackageName(packageName)
        }
    }

    private fun createAppSettings(
        activityInfo: LauncherActivityInfo,
        appsSettings: Set<AppSettingsModel>
    ): AppSettingsModel {
        val componentName = activityInfo.componentName
        val categoryId = packageDao.getCategoryId(componentName.packageName) ?: OTHER.id
        var position = -1
        appsSettings.forEach {
            if (it.categoryId == categoryId && it.position > position) {
                position = it.position
            }
        }
        return appSettingsFactory.create(componentName, categoryId, position + 1)
    }

    private fun isSame(activityInfo: LauncherActivityInfo, appSettings: AppSettingsModel): Boolean {
        val componentName = activityInfo.componentName
        return appSettings.packageName == componentName.packageName &&
                appSettings.className == componentName.className
    }

    private fun MutableList<AppModel>.update(element: AppModel) {
        removeAll { it.id == element.id }
        add(element)
    }
}