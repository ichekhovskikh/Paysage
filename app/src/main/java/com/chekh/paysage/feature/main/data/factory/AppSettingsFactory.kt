package com.chekh.paysage.feature.main.data.factory

import android.content.pm.LauncherActivityInfo
import android.content.pm.PackageManager
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.data.model.entity.AppSettingsEntity
import com.chekh.paysage.data.model.IconColor
import com.chekh.paysage.extension.toIconColor
import javax.inject.Inject

interface AppSettingsFactory {

    fun create(
        activityInfo: LauncherActivityInfo,
        categoryId: String,
        position: Int,
        isHidden: Boolean = false
    ): AppSettingsEntity
}

class AppSettingsFactoryImpl @Inject constructor(
    private val packageManager: PackageManager
) : AppSettingsFactory {

    override fun create(
        activityInfo: LauncherActivityInfo,
        categoryId: String,
        position: Int,
        isHidden: Boolean
    ): AppSettingsEntity {
        val componentName = activityInfo.componentName
        val packageName = componentName.packageName
        val className = componentName.className
        val id = packageName + className
        val applicationInfo = activityInfo.applicationInfo
        val title = applicationInfo.loadLabel(packageManager).toString()
        val icon = applicationInfo.loadIcon(packageManager).toBitmap()
        val iconColor = icon.toIconColor()

        return AppSettingsEntity(
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