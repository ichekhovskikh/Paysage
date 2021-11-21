package com.chekh.paysage.feature.main.apps.presentation.adapter.payload

import com.chekh.paysage.feature.main.common.domain.model.AppModel
import com.chekh.paysage.feature.main.apps.presentation.model.AppGroupModel

data class AppGroupAppsChanged(val apps: List<AppModel>)

fun isAppGroupAppsChanged(
    oldItem: AppGroupModel,
    newItem: AppGroupModel
) = oldItem.data.category == newItem.data.category
