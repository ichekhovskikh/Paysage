package com.chekh.paysage.feature.main.common.data.mapper

import com.chekh.paysage.common.data.model.CategorySettingsEntity
import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.feature.main.common.domain.model.CategoryModel
import javax.inject.Inject

class CategoryModelMapper @Inject constructor() :
    OneParameterMapper<CategorySettingsEntity, CategoryModel> {

    override fun map(source: CategorySettingsEntity) = CategoryModel(
        id = source.id,
        category = source.category,
        position = source.position
    )
}
