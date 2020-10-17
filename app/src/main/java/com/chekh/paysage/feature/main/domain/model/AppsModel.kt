package com.chekh.paysage.feature.main.domain.model

import com.chekh.paysage.common.domain.model.AppSettingsModel

data class AppsModel(
    val apps: List<AppModel>,
    val settings: AppSettingsModel
)
