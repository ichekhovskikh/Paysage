package com.chekh.paysage.feature.home.apps.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.model.CategoryInfo

@Entity
data class AppsGroupByCategory(
    @Embedded
    var category: CategoryInfo? = null,
    @Relation(entity = AppInfo::class, parentColumn = "id", entityColumn = "categoryId")
    var apps: List<AppInfo> = emptyList()
)