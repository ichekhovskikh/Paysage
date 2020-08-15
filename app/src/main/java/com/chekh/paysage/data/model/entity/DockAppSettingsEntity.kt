package com.chekh.paysage.data.model.entity

import androidx.room.*
import com.chekh.paysage.data.model.entity.AppSettingsEntity

@Entity(tableName = "dock_app")
data class DockAppSettingsEntity(

    @PrimaryKey
    var id: String = "",

    @ForeignKey(
        entity = AppSettingsEntity::class,
        parentColumns = ["id"],
        childColumns = ["appId"]
    )

    var appId: String = "",

    var position: Int = Int.MAX_VALUE
)
