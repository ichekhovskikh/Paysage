package com.chekh.paysage.feature.main.domain.model

data class AppsGroupByCategoryModel(
    val category: CategoryModel,
    val apps: List<AppModel>
)
