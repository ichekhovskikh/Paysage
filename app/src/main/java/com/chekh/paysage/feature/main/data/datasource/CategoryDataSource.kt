package com.chekh.paysage.feature.main.data.datasource

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.CategoryDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.data.mapper.CategoryModelMapper
import com.chekh.paysage.feature.main.domain.model.CategoryModel
import javax.inject.Inject

interface CategoryDataSource {
    val categories: LiveData<List<CategoryModel>>
}

class CategoryDataSourceImpl @Inject constructor(
    categoryDao: CategoryDao,
    private val categoryMapper: CategoryModelMapper
) : CategoryDataSource {

    override val categories = categoryDao.getAll()
        .foreachMap(categoryMapper::map)
}
