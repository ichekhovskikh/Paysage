package com.chekh.paysage.feature.home.data.model

import androidx.room.*

@Entity(tableName = "dock_app")
data class DockAppSettingsModel(
    @PrimaryKey
    var id: String = "",
    @ForeignKey(entity = AppSettingsModel::class, parentColumns = ["id"], childColumns = ["appId"])
    var appId: String = "",
    var position: Int = Int.MAX_VALUE
)
