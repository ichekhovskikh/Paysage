package com.chekh.paysage.common.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "package")
data class PackageSettingsEntity(
    @PrimaryKey
    var name: String,
    @ForeignKey(
        entity = CategorySettingsEntity::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = CASCADE
    )
    var categoryId: String
)
