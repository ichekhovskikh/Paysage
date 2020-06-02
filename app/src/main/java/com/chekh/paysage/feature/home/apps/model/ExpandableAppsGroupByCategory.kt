package com.chekh.paysage.feature.home.apps.model

data class ExpandableAppsGroupByCategory(
    val data: AppsGroupByCategory,
    val isExpanded: Boolean = false,
    val scrollOffset: Int = 0
)