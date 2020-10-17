package com.chekh.paysage.common.data.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "category",
    indices = [Index(value = ["category"], unique = true)]
)
data class CategorySettingsEntity(
    @PrimaryKey
    var id: String = "",
    var category: AppCategory = AppCategory.OTHER,
    var position: Int = 0
)
