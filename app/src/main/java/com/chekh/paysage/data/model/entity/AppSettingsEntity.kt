package com.chekh.paysage.data.model.entity

import android.graphics.Bitmap
import androidx.room.*
import com.chekh.paysage.data.model.IconColor

@Entity(
    tableName = "app",
    indices = [Index(value = ["packageName", "className"], unique = true)]
)
data class AppSettingsEntity(

    @PrimaryKey
    var id: String = "",

    var title: String = "",

    var packageName: String = "",

    var className: String = "",

    @ForeignKey(
        entity = CategorySettingsEntity::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"]
    )
    var categoryId: String = "",

    var position: Int = Int.MAX_VALUE,

    var isHidden: Boolean = false,

    var icon: Bitmap? = null,

    var iconColor: IconColor = IconColor.NOTHING
)
