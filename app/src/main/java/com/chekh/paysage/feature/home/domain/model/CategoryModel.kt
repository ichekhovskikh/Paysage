package com.chekh.paysage.feature.home.domain.model

import com.chekh.paysage.feature.home.data.model.AppCategory

data class CategoryModel(
    val id: String,
    val category: AppCategory = AppCategory.OTHER,
    val position: Int
)