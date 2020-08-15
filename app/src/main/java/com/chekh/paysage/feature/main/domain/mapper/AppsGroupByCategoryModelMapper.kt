package com.chekh.paysage.feature.main.domain.mapper

import com.chekh.paysage.feature.main.domain.model.*
import com.chekh.paysage.mapper.ThreeParametersMapper
import javax.inject.Inject

class AppsGroupByCategoryModelMapper @Inject constructor() :
    ThreeParametersMapper<CategoryModel, List<AppModel>, AppSettingsModel, AppsGroupByCategoryModel> {

    override fun map(
        firstSource: CategoryModel,
        secondSource: List<AppModel>,
        thirdSource: AppSettingsModel
    ): AppsGroupByCategoryModel {
        return AppsGroupByCategoryModel(
            category = firstSource,
            apps = secondSource,
            appSettings = thirdSource
        )
    }
}