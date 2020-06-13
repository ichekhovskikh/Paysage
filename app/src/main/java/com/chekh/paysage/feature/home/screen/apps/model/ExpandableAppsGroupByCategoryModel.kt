package com.chekh.paysage.feature.home.screen.apps.model

import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel

data class ExpandableAppsGroupByCategoryModel(
    val data: AppsGroupByCategoryModel,
    val isExpanded: Boolean = false,
    val scrollOffset: Int = 0
)