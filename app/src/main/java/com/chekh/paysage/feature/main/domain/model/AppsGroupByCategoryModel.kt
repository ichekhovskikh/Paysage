package com.chekh.paysage.feature.main.domain.model

import com.chekh.paysage.common.domain.model.AppSettingsModel

data class AppsGroupByCategoryModel(
    val category: CategoryModel,
    val apps: List<AppModel>,
    val settings: AppSettingsModel
)
