package com.chekh.paysage.model

import android.content.Intent
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "app")
data class AppInfo(
    @PrimaryKey
    val packageName: String,
    var className: String,
    var title: String,
    var icon: Bitmap,
    var categoryId: String,
    var position: Int,
    var isHidden: Boolean,
    var iconColor: IconColor = IconColor.NOTHING
) {
    @Ignore
    val intent = Intent(Intent.ACTION_MAIN).apply {
        addCategory(Intent.CATEGORY_LAUNCHER)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        setClassName(packageName, className)
    }
}
