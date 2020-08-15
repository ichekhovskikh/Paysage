package com.chekh.paysage.data.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.chekh.paysage.data.model.entity.CategorySettingsEntity

@Entity(tableName = "package")
data class PackageSettingsEntity(

    @PrimaryKey
    var name: String,

    @ForeignKey(
        entity = CategorySettingsEntity::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"]
    )
    var categoryId: String
)