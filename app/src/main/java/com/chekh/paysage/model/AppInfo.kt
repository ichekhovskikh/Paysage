package com.chekh.paysage.model

import android.content.ComponentName
import android.content.Intent
import android.content.pm.LauncherActivityInfo
import android.graphics.Bitmap
import androidx.core.graphics.drawable.toBitmap
import androidx.room.*
import com.chekh.paysage.PaysageApp.Companion.appManager

@Entity(tableName = "app", indices = [Index(value = ["packageName", "className"], unique = true)])
class AppInfo {
    @PrimaryKey
    var id: String
    var packageName: String
    var className: String
    var title: String
    var icon: Bitmap
    @ForeignKey(entity = CategoryInfo::class, parentColumns = ["id"], childColumns = ["categoryId"])
    var categoryId: String
    var position: Int
    var isHidden: Boolean
    var iconColor: IconColor = IconColor.NOTHING
    @Ignore
    var intent: Intent

    constructor(
        packageName: String,
        className: String,
        title: String,
        icon: Bitmap,
        categoryId: String,
        position: Int,
        isHidden: Boolean,
        iconColor: IconColor = IconColor.NOTHING
    ) {
        this.packageName = packageName
        this.className = className
        this.title = title
        this.icon = icon
        this.categoryId = categoryId
        this.position = position
        this.isHidden = isHidden
        this.iconColor = iconColor
        this.id = packageName + className
        this.intent = createIntent(packageName, className)
    }

    constructor(categoryId: String, activityInfo: LauncherActivityInfo) {
        val applicationInfo = activityInfo.applicationInfo
        val icon = applicationInfo.loadIcon(appManager.packageManager).toBitmap()  /*TODO IconPack*/
        this.packageName = activityInfo.componentName.packageName
        this.className = activityInfo.componentName.className
        this.title = applicationInfo.loadLabel(appManager.packageManager).toString()
        this.icon = icon
        this.categoryId = categoryId
        this.position = POSITION_FOR_NEW_APP
        this.isHidden = false
        this.iconColor = IconColor.get(icon)
        this.id = packageName + className
        this.intent = createIntent(packageName, className)
    }

    private fun createIntent(packageName: String, className: String) = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        setClassName(packageName, className)
    }

    fun equalsComponentName(componentName: ComponentName): Boolean {
        return equalsComponentName(componentName.packageName, componentName.className)
    }

    fun equalsComponentName(packageName: String, className: String): Boolean {
        return this.packageName == packageName && this.className == className
    }

    companion object {
        private const val POSITION_FOR_NEW_APP = Int.MAX_VALUE
    }
}
