package com.chekh.paysage.feature.home.domain.mapper

import com.chekh.paysage.feature.home.domain.model.AppModel
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.home.domain.model.AppListModel
import com.chekh.paysage.feature.home.domain.model.CategoryModel
import com.chekh.paysage.mapper.FourParametersMapper
import javax.inject.Inject

class AppsGroupByCategoryModelMapper @Inject constructor() :
    FourParametersMapper<CategoryModel, List<AppModel>, Int, Int, AppsGroupByCategoryModel> {

    override fun map(
        firstSource: CategoryModel,
        secondSource: List<AppModel>,
        thirdSource: Int,
        fourthSource: Int
    ): AppsGroupByCategoryModel {
        return AppsGroupByCategoryModel(
            category = firstSource,
            appList = AppListModel(secondSource, thirdSource, fourthSource)
        )
    }
}