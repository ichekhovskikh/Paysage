package com.chekh.paysage.feature.main.domain.mapper

import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.mapper.ThreeParametersMapper
import com.chekh.paysage.feature.main.domain.model.*
import javax.inject.Inject

class AppsGroupByCategoryModelMapper @Inject constructor() :
    ThreeParametersMapper<CategoryModel, List<AppModel>, AppSettingsModel, AppsGroupByCategoryModel> {

    override fun map(
        firstSource: CategoryModel,
        secondSource: List<AppModel>,
        thirdSource: AppSettingsModel
    ) = AppsGroupByCategoryModel(
        category = firstSource,
        apps = secondSource,
        settings = thirdSource
    )
}
