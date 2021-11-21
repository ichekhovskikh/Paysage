package com.chekh.paysage.feature.main.common.domain.mapper

import com.chekh.paysage.core.mapper.TwoParametersMapper
import com.chekh.paysage.feature.main.common.domain.model.*
import javax.inject.Inject

class AppsGroupByCategoryModelMapper @Inject constructor() :
    TwoParametersMapper<CategoryModel, List<AppModel>, AppsGroupByCategoryModel> {

    override fun map(
        firstSource: CategoryModel,
        secondSource: List<AppModel>
    ) = AppsGroupByCategoryModel(
        category = firstSource,
        apps = secondSource
    )
}
