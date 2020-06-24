package com.chekh.paysage.feature.home.data.factory

import android.content.pm.LauncherActivityInfo
import android.content.pm.PackageManager
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.feature.home.data.model.AppSettingsModel
import com.chekh.paysage.feature.home.data.model.IconColor
import javax.inject.Inject

interface AppSettingsFactory {

    fun create(
        activityInfo: LauncherActivityInfo,
        categoryId: String,
        position: Int,
        isHidden: Boolean = false
    ): AppSettingsModel
}

class AppSettingsFactoryImpl @Inject constructor(
    private val packageManager: PackageManager
) : AppSettingsFactory {

    override fun create(
        activityInfo: LauncherActivityInfo,
        categoryId: String,
        position: Int,
        isHidden: Boolean
    ): AppSettingsModel {
        val componentName = activityInfo.componentName
        val packageName = componentName.packageName
        val className = componentName.className
        val id = packageName + className
        val applicationInfo = activityInfo.applicationInfo
        val title = applicationInfo.loadLabel(packageManager).toString()
        val icon = applicationInfo.loadIcon(packageManager)
        val iconColor = IconColor.get(icon.toBitmap())

        return AppSettingsModel(
            id = id,
            title = title,
            packageName = packageName,
            className = className,
            categoryId = categoryId,
            position = position,
            isHidden = isHidden,
            icon = icon,
            iconColor = iconColor
        )
    }
}