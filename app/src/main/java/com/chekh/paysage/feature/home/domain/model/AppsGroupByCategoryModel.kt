package com.chekh.paysage.feature.home.domain.model

data class AppsGroupByCategoryModel(
    val category: CategoryModel,
    val apps: List<AppModel>
)