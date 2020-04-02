package com.chekh.paysage.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "category", indices = [Index(value = ["title"], unique = true)])
data class CategoryInfo(
    @PrimaryKey
    var id: String = "",
    var title: CategoryTitle = CategoryTitle.OTHER,
    var position: Int = 0,
    var isHidden: Boolean = false
)