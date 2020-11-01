package com.chekh.paysage.feature.main.presentation.apps.mapper

import com.chekh.paysage.core.mapper.ThreeParametersMapper
import com.chekh.paysage.feature.main.domain.model.*
import com.chekh.paysage.feature.main.presentation.apps.model.ExpandableAppsGroupByCategoryModel
import javax.inject.Inject

class ExpandableAppsGroupByCategoryModelMapper @Inject constructor() :
    ThreeParametersMapper<AppsGroupByCategoryModel, Boolean, Int, ExpandableAppsGroupByCategoryModel> {

    override fun map(
        firstSource: AppsGroupByCategoryModel,
        secondSource: Boolean,
        thirdSource: Int
    ) = ExpandableAppsGroupByCategoryModel(
        data = firstSource,
        isExpanded = secondSource,
        scrollOffset = thirdSource
    )
}
