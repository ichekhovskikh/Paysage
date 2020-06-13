package com.chekh.paysage.feature.home.data

import androidx.lifecycle.LiveData
import com.chekh.paysage.extension.zip
import com.chekh.paysage.feature.home.domain.HomeRepository
import com.chekh.paysage.feature.home.data.service.AppService
import com.chekh.paysage.feature.home.data.service.CategoryService
import com.chekh.paysage.feature.home.domain.mapper.AppsGroupByCategoryModelMapper
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val appService: AppService,
    private val appsGroupByCategoryMapper: AppsGroupByCategoryModelMapper
) : HomeRepository {

    override fun getAppsGroupByCategories(): LiveData<List<AppsGroupByCategoryModel>> = zip(
        categoryService.categoriesLiveData,
        appService.appsLiveData
    ) { categories, apps ->

        val appsGroupByCategories = mutableListOf<AppsGroupByCategoryModel>()
        val sortedCategories = categories?.sortedBy { it.position }
        val sortedApps = apps?.sortedBy { it.position }

        sortedCategories?.forEach { category ->
            val categoryApps = sortedApps?.filter { it.categoryId == category.id && !it.isHidden }
            if (categoryApps != null && categoryApps.isNotEmpty()) {
                val appsGroupByCategory = appsGroupByCategoryMapper.map(category, categoryApps)
                appsGroupByCategories.add(appsGroupByCategory)
            }
        }
        appsGroupByCategories
    }

    override fun startObserveUpdates() {
        appService.startObserveUpdates()
    }

    override fun stopObserveUpdates() {
        appService.stopObserveUpdates()
    }
}