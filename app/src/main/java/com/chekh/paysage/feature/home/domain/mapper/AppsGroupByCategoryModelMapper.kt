package com.chekh.paysage.feature.home.domain.mapper

import com.chekh.paysage.feature.home.domain.model.AppModel
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.home.domain.model.CategoryModel
import com.chekh.paysage.mapper.TwoParametersMapper
import javax.inject.Inject

class AppsGroupByCategoryModelMapper @Inject constructor() :
    TwoParametersMapper<CategoryModel, List<AppModel>, AppsGroupByCategoryModel> {

    override fun map(
        firstSource: CategoryModel,
        secondSource: List<AppModel>
    ): AppsGroupByCategoryModel {
        return AppsGroupByCategoryModel(
            category = firstSource,
            apps = secondSource
        )
    }
}