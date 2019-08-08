package com.chekh.paysage.db

import android.content.pm.LauncherActivityInfo
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.model.CategoryTitle
import com.chekh.paysage.model.IconColor
import com.chekh.paysage.util.CATEGORIES_FILE_NAME
import com.chekh.paysage.util.readDefaultPackages

object AppRepository {
    private val database = PaysageApp.database
    private val packageManager = PaysageApp.appManager.packageManager

    fun getAppsGroupByCategories() = database.categoryDao().getGroupByCategories()

    fun removeApps(packageName: String) = database.appDao().removeByPackageName(packageName)

    fun reInitApps(activityInfos: List<LauncherActivityInfo>) {
        val recentApps = database.appDao().getAll()
        val allApps = mergeApps(activityInfos, recentApps)
        database.runInTransaction {
            database.appDao().removeAll()
            database.appDao().add(allApps)
        }
    }

    fun updateApps(activityInfos: List<LauncherActivityInfo>) {
        if (activityInfos.isNotEmpty()) {
            val recentApps = database.appDao().getByPackageName(activityInfos.first().componentName.packageName)
            val updatedApps = mergeApps(activityInfos, recentApps)
            database.appDao().add(updatedApps)
        }
    }

    fun prepopulate() {
        database.runInTransaction {
            prepopulateCategories()
            prepopulatePackages()
        }
    }

    private fun prepopulateCategories() {
        var position = 0
        val categories = CategoryTitle.values().map { CategoryInfo(it.id, it, position++, false) }
        database.categoryDao().add(categories)
    }

    private fun prepopulatePackages() {
        val packages = readDefaultPackages(CATEGORIES_FILE_NAME)
        database.packageDao().add(packages)
    }

    private fun mergeApps(activityInfos: List<LauncherActivityInfo>, recentApps: List<AppInfo>): MutableList<AppInfo> {
        val mergedApps = mutableListOf<AppInfo>()
        activityInfos.forEach { activityInfo ->
            val applicationInfo = activityInfo.applicationInfo
            val app = recentApps.find { it.equalsComponentName(activityInfo.componentName) }
            if (app == null) {
                val categoryId = database.packageDao()
                    .getCategoryId(activityInfo.componentName.packageName) ?: CategoryTitle.OTHER.id
                mergedApps.add(AppInfo(categoryId, activityInfo))
            } else {
                val icon = applicationInfo.loadIcon(packageManager).toBitmap()  /*TODO IconPack*/
                app.title = applicationInfo.loadLabel(packageManager).toString()
                app.icon = icon
                app.iconColor = IconColor.get(icon)
                mergedApps.add(app)
            }
        }
        return mergedApps
    }
}