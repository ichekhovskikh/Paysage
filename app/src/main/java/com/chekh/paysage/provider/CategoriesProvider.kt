package com.chekh.paysage.provider

import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.model.CategoryTitle

class CategoriesProvider : Provider<List<CategoryInfo>> {

    override fun provide(): List<CategoryInfo> {
        return CategoryTitle.values().map { CategoryInfo(it.id, it, it.ordinal, false) }
    }
}