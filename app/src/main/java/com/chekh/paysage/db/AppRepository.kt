package com.chekh.paysage.db

import android.content.pm.LauncherActivityInfo
import android.os.UserHandle
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.manager.AppManager
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.CategoryTitle
import com.chekh.paysage.model.IconColor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object AppRepository {
    private val database = PaysageApp.database
    private val appManager = PaysageApp.appManager

    fun getAppsGroupByCategories() = database.categoryDao().getGroupByCategories()

    fun initApps() {
        val activityInfos = appManager.getAllApps()
        if (activityInfos.isNotEmpty()) {
            val recentApps = database.appDao().getAll()
            val allApps = mergeApps(activityInfos, recentApps)
            database.runInTransaction {
                database.appDao().removeAll()
                database.appDao().add(allApps)
            }
        }
    }

    fun enableObserveAppsChanging() {
        appManager.addOnAppsChangedCallback(appsChangedCallback)
    }

    fun disableObserveAppsChanging() {
        appManager.removeOnAppsChangedCallback(appsChangedCallback)
    }

    private val appsChangedCallback = object : AppManager.AppsChangedCallback() {

        override fun onPackageRemoved(packageName: String, userHandle: UserHandle) {
            GlobalScope.launch {
                database.appDao().removeByPackageName(packageName)
            }
        }

        override fun onPackageAdded(packageName: String, userHandle: UserHandle) {
            onPackageChanged(packageName, userHandle)
        }

        override fun onPackageChanged(packageName: String, userHandle: UserHandle) {
            GlobalScope.launch {
                val activityInfos = appManager.getApps(packageName, userHandle)
                if (activityInfos.isNotEmpty()) {
                    val componentName = activityInfos.first().componentName.packageName
                    val recentApps = database.appDao().getByPackageName(componentName)
                    val updatedApps = mergeApps(activityInfos, recentApps)
                    database.appDao().add(updatedApps)
                }
            }
        }
    }

    private fun mergeApps(
        activityInfos: List<LauncherActivityInfo>,
        recentApps: List<AppInfo>
    ): MutableList<AppInfo> {
        val mergedApps = mutableListOf<AppInfo>()
        activityInfos.forEach { activityInfo ->
            val applicationInfo = activityInfo.applicationInfo
            val app = recentApps.find { it.equalsComponentName(activityInfo.componentName) }
            if (app == null) {
                val categoryId = database.packageDao()
                    .getCategoryId(activityInfo.componentName.packageName) ?: CategoryTitle.OTHER.id
                mergedApps.add(AppInfo(categoryId, activityInfo))
            } else {
                val icon = applicationInfo.loadIcon(appManager.packageManager)
                    .toBitmap()  /*TODO IconPack*/
                app.title = applicationInfo.loadLabel(appManager.packageManager).toString()
                app.icon = icon
                app.iconColor = IconColor.get(icon)
                mergedApps.add(app)
            }
        }
        return mergedApps
    }
}