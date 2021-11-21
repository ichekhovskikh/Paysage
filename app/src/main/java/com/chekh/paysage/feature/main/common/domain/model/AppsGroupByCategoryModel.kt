package com.chekh.paysage.feature.main.common.domain.model

data class AppsGroupByCategoryModel(
    val category: CategoryModel,
    val apps: List<AppModel>
)
