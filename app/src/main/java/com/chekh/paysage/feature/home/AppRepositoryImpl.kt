package com.chekh.paysage.feature.home

import android.os.UserHandle
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.manager.AppManager
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.CategoryTitle
import com.chekh.paysage.model.IconColor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppRepositoryImpl : AppRepository {
    private val database = PaysageApp.database
    private val appManager = PaysageApp.appManager

    override fun getAppsGroupByCategories() = database.categoryDao.getGroupByCategories()

    override fun initApps() {
        updateAllApps()
    }

    override fun enableObserveAppsChanging() {
        appManager.addOnAppsChangedCallback(appsChangedCallback)
    }

    override fun disableObserveAppsChanging() {
        appManager.removeOnAppsChangedCallback(appsChangedCallback)
    }

    private fun updateAllApps() {
        val activityInfos = appManager.getAllApps()
        if (activityInfos.isEmpty()) {
            database.appDao.removeAll()
            return
        }
        val recentApps = database.appDao.getAll().toMutableList()
        activityInfos.forEach { activityInfo ->
            val app = recentApps.find { it.isSame(activityInfo) }
            if (app == null) {
                val componentName = activityInfo.componentName
                val categoryId = database.packageDao.getCategoryId(componentName.packageName)
                    ?: CategoryTitle.OTHER.id
                recentApps.add(AppInfo.create(categoryId, activityInfo))
            } else {
                val applicationInfo = activityInfo.applicationInfo
                app.title = applicationInfo.loadLabel(appManager.packageManager).toString()
                val icon = applicationInfo.loadIcon(appManager.packageManager) /*TODO IconPack*/
                app.icon = icon
                app.iconColor = IconColor.get(icon.toBitmap())
            }
        }
        database.appDao.add(recentApps)
    }

    private val appsChangedCallback = object : AppManager.AppsChangedCallback() {

        override fun onPackageRemoved(packageName: String, userHandle: UserHandle) {
            GlobalScope.launch {
                database.appDao.removeByPackageName(packageName)
            }
        }

        override fun onPackageAdded(packageName: String, userHandle: UserHandle) {
            onPackageChanged(packageName, userHandle)
        }

        override fun onPackageChanged(packageName: String, userHandle: UserHandle) {
            /*GlobalScope.launch {
                val activityInfos = appManager.getApps(packageName, userHandle)
                if (activityInfos.isNotEmpty()) {
                    val componentName = activityInfos.first().componentName.packageName
                    val recentApps = database.appDao.getByPackageName(componentName)
                    val updatedApps = mergeApps(
                        activityInfos,
                        recentApps
                    )
                    database.appDao.add(updatedApps)
                }
            }*/
        }
    }
}