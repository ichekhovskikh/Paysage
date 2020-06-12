package com.chekh.paysage.provider

import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.model.CategoryTitle

interface CategoriesProvider {
    fun provide(): List<CategoryInfo>
}

class CategoriesProviderImpl : CategoriesProvider {

    override fun provide(): List<CategoryInfo> {
        return CategoryTitle.values().map { CategoryInfo(it.id, it, it.ordinal) }
    }
}