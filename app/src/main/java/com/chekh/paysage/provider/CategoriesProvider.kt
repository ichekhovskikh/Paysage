package com.chekh.paysage.provider

import com.chekh.paysage.data.model.entity.CategorySettingsEntity
import com.chekh.paysage.data.model.AppCategory
import javax.inject.Inject

interface CategoriesProvider {
    fun provide(): List<CategorySettingsEntity>
}

class CategoriesProviderImpl @Inject constructor() : CategoriesProvider {

    override fun provide(): List<CategorySettingsEntity> {
        return AppCategory.values().map {
            CategorySettingsEntity(
                it.id,
                it,
                it.ordinal
            )
        }
    }
}