package com.chekh.paysage.feature.main.presentation.apps.data

import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.model.AppModel

data class AppGroupAppsChanged(
    val apps: List<AppModel>,
    val appSettings: AppSettingsModel
)
