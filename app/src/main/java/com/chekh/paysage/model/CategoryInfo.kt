package com.chekh.paysage.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "category", indices = [Index(value = ["title"], unique = true)])
data class CategoryInfo(
    @PrimaryKey
    var id: String,
    var title: CategoryTitle,
    var position: Int,
    var isHidden: Boolean
)