package com.chekh.paysage.provider

import com.chekh.paysage.feature.home.data.model.CategorySettingsModel
import com.chekh.paysage.feature.home.data.model.AppCategory
import javax.inject.Inject

interface CategoriesProvider {
    fun provide(): List<CategorySettingsModel>
}

class CategoriesProviderImpl @Inject constructor() : CategoriesProvider {

    override fun provide(): List<CategorySettingsModel> {
        return AppCategory.values().map {
            CategorySettingsModel(
                it.id,
                it,
                it.ordinal
            )
        }
    }
}