package com.chekh.paysage.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity
class AppsGroupByCategory {
    @Embedded
    lateinit var category: CategoryInfo
    @Relation(entity = AppInfo::class, parentColumn = "id", entityColumn = "categoryId")
    lateinit var apps: List<AppInfo>
}