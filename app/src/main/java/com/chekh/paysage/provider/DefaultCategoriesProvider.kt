package com.chekh.paysage.provider

import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.model.CategoryTitle

object DefaultCategoriesProvider {

    fun getDefaultCategories(): List<CategoryInfo> {
        var position = 0
        return CategoryTitle.values().map { CategoryInfo(it.id, it, position++, false) }
    }
}