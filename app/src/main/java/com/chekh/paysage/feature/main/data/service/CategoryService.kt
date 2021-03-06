package com.chekh.paysage.feature.main.data.service

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.data.dao.CategoryDao
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.feature.main.data.mapper.CategoryModelMapper
import com.chekh.paysage.feature.main.domain.model.CategoryModel
import javax.inject.Inject

interface CategoryService {
    val categoriesLiveData: LiveData<List<CategoryModel>>
}

class CategoryServiceImpl @Inject constructor(
    categoryDao: CategoryDao,
    private val categoryMapper: CategoryModelMapper
) : CategoryService {

    override val categoriesLiveData = categoryDao.getAllLive()
        .foreachMap { categoryMapper.map(it) }
}
