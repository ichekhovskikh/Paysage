package com.chekh.paysage.common.data.model

import android.graphics.Bitmap
import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(
    tableName = "app",
    indices = [Index(value = ["packageName", "className"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = CategorySettingsEntity::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = CASCADE
        )
    ]
)
data class AppSettingsEntity(
    @PrimaryKey
    var id: String = "",
    var title: String = "",
    var packageName: String = "",
    var className: String = "",
    var categoryId: String = "",
    var boardPosition: Int = Int.MAX_VALUE,
    var dockPosition: Int = -1,
    var isHidden: Boolean = false,
    var icon: Bitmap? = null,
    var iconColor: IconColor = IconColor.NOTHING
)
