package com.chekh.paysage.feature.main.domain.mapper

import com.chekh.paysage.data.model.entity.CategorySettingsEntity
import com.chekh.paysage.feature.main.domain.model.CategoryModel
import com.chekh.paysage.mapper.OneParameterMapper
import javax.inject.Inject

class CategoryModelMapper @Inject constructor() :
    OneParameterMapper<CategorySettingsEntity, CategoryModel> {

    override fun map(source: CategorySettingsEntity): CategoryModel = source.run {
        CategoryModel(
            id = id,
            category = category,
            position = position
        )
    }
}