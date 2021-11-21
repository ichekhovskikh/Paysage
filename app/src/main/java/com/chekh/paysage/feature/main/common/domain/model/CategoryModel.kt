package com.chekh.paysage.feature.main.common.domain.model

import com.chekh.paysage.common.data.model.AppCategory

data class CategoryModel(
    val id: String,
    val category: AppCategory = AppCategory.OTHER,
    val position: Int
)
