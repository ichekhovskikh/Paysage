package com.chekh.paysage.core.provider.database

import com.chekh.paysage.common.data.model.AppCategory
import com.chekh.paysage.common.data.model.CategorySettingsEntity
import javax.inject.Inject

interface CategoriesProvider {
    fun provide(): List<CategorySettingsEntity>
}

class CategoriesProviderImpl @Inject constructor() : CategoriesProvider {

    override fun provide(): List<CategorySettingsEntity> {
        return AppCategory.values().map {
            CategorySettingsEntity(it.id, it, it.ordinal)
        }
    }
}
