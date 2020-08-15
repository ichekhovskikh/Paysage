package com.chekh.paysage.data.model.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.chekh.paysage.data.model.AppCategory

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