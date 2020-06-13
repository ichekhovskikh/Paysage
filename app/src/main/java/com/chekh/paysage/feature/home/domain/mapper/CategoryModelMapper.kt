package com.chekh.paysage.feature.home.domain.mapper

import com.chekh.paysage.feature.home.data.model.CategorySettingsModel
import com.chekh.paysage.feature.home.domain.model.CategoryModel
import com.chekh.paysage.mapper.OneParameterMapper
import javax.inject.Inject

class CategoryModelMapper @Inject constructor() :
    OneParameterMapper<CategorySettingsModel, CategoryModel> {

    override fun map(source: CategorySettingsModel): CategoryModel = source.run {
        CategoryModel(
            id = id,
            category = category,
            position = position
        )
    }
}