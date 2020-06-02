package com.chekh.paysage.model

import android.content.Intent
import android.content.pm.LauncherActivityInfo
import android.graphics.drawable.Drawable
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
    var icon: Drawable? = null,
    @ForeignKey(entity = CategoryInfo::class, parentColumns = ["id"], childColumns = ["categoryId"])
    var categoryId: String = "",
    var position: Int = POSITION_FOR_NEW_APP,
    var isHidden: Boolean = false,
    var iconColor: IconColor = IconColor.NOTHING
) {

    @Ignore
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        setClassName(packageName, className)
    }

    constructor(
        packageName: String,
        className: String,
        title: String,
        icon: Drawable,
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
        id = packageName + className
    )

    fun isSame(app: LauncherActivityInfo): Boolean {
        val componentName = app.componentName
        return packageName == componentName.packageName && className == componentName.className
    }

    companion object {
        fun create(categoryId: String, activityInfo: LauncherActivityInfo): AppInfo {
            val applicationInfo = activityInfo.applicationInfo
            /*TODO IconPack*/
            val icon = applicationInfo.loadIcon(appManager.packageManager)
            val packageName = activityInfo.componentName.packageName
            val className = activityInfo.componentName.className
            val title = applicationInfo.loadLabel(appManager.packageManager).toString()
            val position = POSITION_FOR_NEW_APP
            val isHidden = false
            val iconColor = IconColor.get(icon.toBitmap())
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
    }
}
