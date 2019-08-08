package com.chekh.paysage.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "package")
data class PackageInfo(
    @PrimaryKey
    var name: String,
    @ForeignKey(entity = CategoryInfo::class, parentColumns = ["id"], childColumns = ["categoryId"])
    var categoryId: String
)