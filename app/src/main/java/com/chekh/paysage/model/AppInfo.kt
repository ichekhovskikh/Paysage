package com.chekh.paysage.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app")
data class AppInfo(
    @PrimaryKey
    val packageName: String,
    var title: String,
    var icon: Bitmap,
    var className: String,
    var categoryId: Int,
    var position: Int,
    var isHidden: Boolean,
    var iconColor: IconColor = IconColor.NOTHING
)
