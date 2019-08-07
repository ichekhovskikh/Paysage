package com.chekh.paysage.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class AppsCategoryInfo(
    @PrimaryKey
    val id: String,
    var title: CategoryTitle,
    var position: Int,
    var isHidden: Boolean
)