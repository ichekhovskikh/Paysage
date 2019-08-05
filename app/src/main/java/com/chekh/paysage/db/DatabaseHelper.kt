package com.chekh.paysage.db

import android.content.pm.LauncherActivityInfo
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.IconColor

object DatabaseHelper {
    private val appDao = PaysageDatabase.instance.appDao()
    private val categoryDao = PaysageDatabase.instance.categoryDao()
    private val packageManager = PaysageApp.appManager.packageManager

    fun updateApps(activityInfos: List<LauncherActivityInfo>) {
        val updatedApps = mutableListOf<AppInfo>()
        val recentApps = appDao.getAll()
        activityInfos.forEach { activityInfo ->
            val applicationInfo = activityInfo.applicationInfo
            var app = recentApps.find { it.packageName == applicationInfo.packageName }
            val label = applicationInfo.loadLabel(packageManager).toString()
            val icon = applicationInfo.loadIcon(packageManager).toBitmap()  /*TODO IconPack*/
            if (app == null) {
                val categoryId = 0 /*TODO*/
                val position = recentApps.filter { it.categoryId == categoryId }.size
                app = AppInfo(
                    applicationInfo.packageName,
                    label,
                    icon,
                    applicationInfo.className,
                    categoryId,
                    position,
                    false,
                    IconColor.getIconColor(icon)
                )
                updatedApps.add(app)
            } else {
                app.title = label
                app.icon = icon
                app.iconColor = IconColor.getIconColor(icon) /*TODO not if IconPack*/
                updatedApps.add(app)
            }
        }
        PaysageDatabase.instance.runInTransaction {
            appDao.removeAll()
            appDao.add(updatedApps)
        }
    }
}