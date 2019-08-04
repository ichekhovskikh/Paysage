package com.chekh.paysage.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class AppsCategoryInfo(
    @PrimaryKey
    val id: Long,
    var title: CategoryTitle,
    var icon: Bitmap,
    var position: Int,
    var isHidden: Boolean
)