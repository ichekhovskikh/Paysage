package com.chekh.paysage.feature.main.presentation.apps.model

import com.chekh.paysage.feature.main.domain.model.AppsGroupByCategoryModel

data class ExpandableAppsGroupByCategoryModel(
    val data: AppsGroupByCategoryModel,
    val isExpanded: Boolean = false,
    val scrollOffset: Int = 0
)
