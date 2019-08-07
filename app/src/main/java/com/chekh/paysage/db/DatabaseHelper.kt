package com.chekh.paysage.db

import android.content.Intent
import android.content.pm.LauncherActivityInfo
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.PaysageApp
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.IconColor
import com.chekh.paysage.model.getAppsCategoryId

object DatabaseHelper {
    private const val POSITION_FOR_NEW_APP = Int.MAX_VALUE

    private val appDao = PaysageDatabase.instance.appDao()
    private val categoryDao = PaysageDatabase.instance.categoryDao()
    private val packageManager = PaysageApp.appManager.packageManager

    fun updateApps(activityInfos: List<LauncherActivityInfo>) {
        val updatedApps = mutableListOf<AppInfo>()
        val recentApps = appDao.getAll()

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        activityInfos.forEach { activityInfo ->
            val applicationInfo = activityInfo.applicationInfo
            var app = recentApps.find { it.packageName == applicationInfo.packageName }
            val label = applicationInfo.loadLabel(packageManager).toString()
            val icon = applicationInfo.loadIcon(packageManager).toBitmap()  /*TODO IconPack*/
            if (app == null) {
                app = AppInfo(
                    activityInfo.componentName.packageName,
                    activityInfo.componentName.className,
                    label,
                    icon,
                    getAppsCategoryId(applicationInfo),
                    POSITION_FOR_NEW_APP,
                    false,
                    IconColor.getIconColor(icon)
                )
                updatedApps.add(app)
            } else {
                app.title = label
                app.icon = icon
                app.iconColor = IconColor.getIconColor(icon)
                updatedApps.add(app)
            }
        }
        PaysageDatabase.instance.runInTransaction {
            appDao.removeAll()
            appDao.add(updatedApps)
        }
    }
}