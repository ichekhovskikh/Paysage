package com.chekh.paysage.feature.main.apps.presentation.mapper

import com.chekh.paysage.core.mapper.ThreeParametersMapper
import com.chekh.paysage.feature.main.common.domain.model.*
import com.chekh.paysage.feature.main.apps.presentation.model.AppGroupModel
import javax.inject.Inject

class AppGroupsModelMapper @Inject constructor() :
    ThreeParametersMapper<AppsGroupByCategoryModel, Boolean, Int, AppGroupModel> {

    override fun map(
        firstSource: AppsGroupByCategoryModel,
        secondSource: Boolean,
        thirdSource: Int
    ) = AppGroupModel(
        data = firstSource,
        isExpanded = secondSource,
        scrollOffset = thirdSource
    )
}
