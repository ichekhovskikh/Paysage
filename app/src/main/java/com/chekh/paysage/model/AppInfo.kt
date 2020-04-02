package com.chekh.paysage.model

import android.content.ComponentName
import android.content.Intent
import android.content.pm.LauncherActivityInfo
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.room.*
import com.chekh.paysage.PaysageApp.Companion.appManager

@Entity(tableName = "app", indices = [Index(value = ["packageName", "className"], unique = true)])
data class AppInfo(
    @PrimaryKey
    var id: String = "",
    var packageName: String = "",
    var className: String = "",
    var title: String = "",
    var icon: Bitmap? = null,
    @ForeignKey(entity = CategoryInfo::class, parentColumns = ["id"], childColumns = ["categoryId"])
    var categoryId: String = "",
    var position: Int = POSITION_FOR_NEW_APP,
    var isHidden: Boolean = false,
    var iconColor: IconColor = IconColor.NOTHING,
    @Ignore
    var intent: Intent? = null
) {

    constructor(
        packageName: String,
        className: String,
        title: String,
        icon: Bitmap,
        categoryId: String,
        position: Int,
        isHidden: Boolean,
        iconColor: IconColor = IconColor.NOTHING
    ) : this(
        packageName = packageName,
        className = className,
        title = title,
        icon = icon,
        categoryId = categoryId,
        position = position,
        isHidden = isHidden,
        iconColor = iconColor,
        id = packageName + className,
        intent = createIntent(packageName, className)
    )

    fun equalsComponentName(componentName: ComponentName): Boolean {
        return equalsComponentName(componentName.packageName, componentName.className)
    }

    fun equalsComponentName(packageName: String, className: String): Boolean {
        return this.packageName == packageName && this.className == className
    }

    companion object {
        fun create(categoryId: String, activityInfo: LauncherActivityInfo): AppInfo {
            val applicationInfo = activityInfo.applicationInfo
            /*TODO IconPack*/
            val icon = applicationInfo.loadIcon(appManager.packageManager).toBitmap()
            val packageName = activityInfo.componentName.packageName
            val className = activityInfo.componentName.className
            val title = applicationInfo.loadLabel(appManager.packageManager).toString()
            val position = POSITION_FOR_NEW_APP
            val isHidden = false
            val iconColor = IconColor.get(icon)
            return AppInfo(
                packageName,
                className,
                title,
                icon,
                categoryId,
                position,
                isHidden,
                iconColor
            )
        }

        private const val POSITION_FOR_NEW_APP = Int.MAX_VALUE

        private fun createIntent(packageName: String, className: String) =
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                setClassName(packageName, className)
            }
    }
}
