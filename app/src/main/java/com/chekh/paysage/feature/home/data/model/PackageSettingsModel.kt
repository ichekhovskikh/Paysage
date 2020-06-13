package com.chekh.paysage.feature.home.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "package")
data class PackageSettingsModel(
    @PrimaryKey
    var name: String,
    @ForeignKey(entity = CategorySettingsModel::class, parentColumns = ["id"], childColumns = ["categoryId"])
    var categoryId: String
)