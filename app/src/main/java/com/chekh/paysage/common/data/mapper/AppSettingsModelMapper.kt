package com.chekh.paysage.common.data.mapper

import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.mapper.TwoParametersMapper
import javax.inject.Inject

class AppSettingsModelMapper @Inject constructor() :
    TwoParametersMapper<Int, Int, AppSettingsModel> {

    override fun map(
        firstSource: Int,
        secondSource: Int
    ) = AppSettingsModel(
        appSize = firstSource,
        appColumnCount = secondSource
    )
}
