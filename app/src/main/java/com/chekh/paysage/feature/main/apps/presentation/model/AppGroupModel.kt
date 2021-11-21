package com.chekh.paysage.feature.main.apps.presentation.model

import com.chekh.paysage.feature.main.common.domain.model.AppsGroupByCategoryModel

data class AppGroupModel(
    val data: AppsGroupByCategoryModel,
    val isExpanded: Boolean = false,
    val scrollOffset: Int = 0
)
