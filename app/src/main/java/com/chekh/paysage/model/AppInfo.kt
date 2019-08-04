package com.chekh.paysage.model

import android.content.Intent
import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app")
data class AppInfo(
    @PrimaryKey
    val id: Long,
    var title: String,
    var icon: Bitmap,
    var intent: Intent,
    var categoryId: Int,
    var position: Int,
    var isHidden: Boolean,
    var iconColor: IconColor = IconColor.NOTHING
)
