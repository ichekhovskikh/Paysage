package com.chekh.paysage.feature.home.apps.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.CategoryInfo

@Entity
class AppsGroupByCategory {
    @Embedded
    lateinit var category: CategoryInfo
    @Relation(entity = AppInfo::class, parentColumn = "id", entityColumn = "categoryId")
    lateinit var apps: List<AppInfo>
}