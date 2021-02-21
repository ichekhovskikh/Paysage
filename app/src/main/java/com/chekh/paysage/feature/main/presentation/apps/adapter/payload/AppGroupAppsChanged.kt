package com.chekh.paysage.feature.main.presentation.apps.adapter.payload

import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.presentation.apps.model.AppGroupModel

data class AppGroupAppsChanged(val apps: List<AppModel>)

fun isAppGroupAppsChanged(
    oldItem: AppGroupModel,
    newItem: AppGroupModel
) = oldItem.data.category == newItem.data.category
