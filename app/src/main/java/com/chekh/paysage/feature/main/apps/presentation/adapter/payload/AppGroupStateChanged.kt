package com.chekh.paysage.feature.main.apps.presentation.adapter.payload

import com.chekh.paysage.feature.main.apps.presentation.model.AppGroupModel

data class AppGroupStateChanged(val isExpanded: Boolean, val scrollOffset: Int)

fun isAppGroupStateChanged(
    oldItem: AppGroupModel,
    newItem: AppGroupModel
) = oldItem.isExpanded != newItem.isExpanded || oldItem.scrollOffset != newItem.scrollOffset
