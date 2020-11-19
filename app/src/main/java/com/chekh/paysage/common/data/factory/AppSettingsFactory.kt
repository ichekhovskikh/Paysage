package com.chekh.paysage.common.data.factory

import android.content.pm.LauncherActivityInfo
import androidx.core.graphics.drawable.toBitmap
import com.chekh.paysage.common.data.model.AppSettingsEntity
import com.chekh.paysage.core.extension.toIconColor
import javax.inject.Inject

interface AppSettingsFactory {

    fun create(
        activityInfo: LauncherActivityInfo,
        categoryId: String,
        boardPosition: Int,
        dockPosition: Int = -1,
        isHidden: Boolean = false
    ): AppSettingsEntity
}

class AppSettingsFactoryImpl @Inject constructor() : AppSettingsFactory {

    override fun create(
        activityInfo: LauncherActivityInfo,
        categoryId: String,
        boardPosition: Int,
        dockPosition: Int,
        isHidden: Boolean
    ): AppSettingsEntity {
        val componentName = activityInfo.componentName
        val packageName = componentName.packageName
        val className = componentName.className
        val id = packageName + className
        val title = activityInfo.label.toString()
        val icon = activityInfo.getIcon(0).toBitmap()
        val iconColor = icon.toIconColor()

        return AppSettingsEntity(
            id = id,
            title = title,
            packageName = packageName,
            className = className,
            categoryId = categoryId,
            boardPosition = boardPosition,
            dockPosition = dockPosition,
            isHidden = isHidden,
            icon = icon,
            iconColor = iconColor
        )
    }
}
