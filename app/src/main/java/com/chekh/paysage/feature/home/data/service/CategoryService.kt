package com.chekh.paysage.feature.home.data.service

import androidx.lifecycle.LiveData
import com.chekh.paysage.db.dao.CategoryDao
import com.chekh.paysage.extension.foreachMap
import com.chekh.paysage.feature.home.domain.mapper.CategoryModelMapper
import com.chekh.paysage.feature.home.domain.model.CategoryModel
import javax.inject.Inject

interface CategoryService {
    val categoriesLiveData: LiveData<List<CategoryModel>>
}

class CategoryServiceImpl @Inject constructor(
    categoryDao: CategoryDao,
    private val categoryMapper: CategoryModelMapper
) : CategoryService {

    override val categoriesLiveData = categoryDao.getLiveAll()
        .foreachMap { categoryMapper.map(it) }
}