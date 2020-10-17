package com.chekh.paysage.feature.main.domain.mapper

import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.mapper.TwoParametersMapper
import com.chekh.paysage.feature.main.domain.model.*
import javax.inject.Inject

class AppsModelMapper @Inject constructor() :
    TwoParametersMapper<List<AppModel>, AppSettingsModel, AppsModel> {

    override fun map(
        firstSource: List<AppModel>,
        secondSource: AppSettingsModel
    ) = AppsModel(
        apps = firstSource,
        settings = secondSource
    )
}
