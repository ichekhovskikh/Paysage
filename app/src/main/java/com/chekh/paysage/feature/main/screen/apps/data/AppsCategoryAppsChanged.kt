package com.chekh.paysage.feature.main.screen.apps.data

import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.model.AppSettingsModel

data class AppsCategoryAppsChanged(
    val apps: List<AppModel>,
    val appSettings: AppSettingsModel
)