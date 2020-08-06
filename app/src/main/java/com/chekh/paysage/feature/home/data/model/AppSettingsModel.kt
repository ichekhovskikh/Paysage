package com.chekh.paysage.feature.home.data.model

import android.graphics.Bitmap
import androidx.room.*

@Entity(tableName = "app", indices = [Index(value = ["packageName", "className"], unique = true)])
data class AppSettingsModel(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var packageName: String = "",
    var className: String = "",
    @ForeignKey(entity = CategorySettingsModel::class, parentColumns = ["id"], childColumns = ["categoryId"])
    var categoryId: String = "",
    var position: Int = Int.MAX_VALUE,
    var isHidden: Boolean = false,
    var icon: Bitmap? = null,
    var iconColor: IconColor = IconColor.NOTHING
)
