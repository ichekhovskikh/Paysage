package com.chekh.paysage.feature.home.data

import androidx.lifecycle.LiveData
import com.chekh.paysage.data.service.SettingsService
import com.chekh.paysage.extension.zip
import com.chekh.paysage.feature.home.domain.HomeRepository
import com.chekh.paysage.feature.home.data.service.AppService
import com.chekh.paysage.feature.home.data.service.CategoryService
import com.chekh.paysage.feature.home.data.service.DockAppService
import com.chekh.paysage.feature.home.domain.mapper.AppsGroupByCategoryModelMapper
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val settingsService: SettingsService,
    private val appService: AppService,
    private val dockAppService: DockAppService,
    private val appsGroupByCategoryMapper: AppsGroupByCategoryModelMapper
) : HomeRepository {

    override fun getDockAppsSize() = settingsService.dockAppsSizeLiveData

    override fun getDockAppsPositions() = dockAppService.dockAppsPositions

    override fun getDockApps() = dockAppService.dockAppsLiveData

    override fun getAppsGroupByCategories(): LiveData<List<AppsGroupByCategoryModel>> = zip(
        categoryService.categoriesLiveData,
        appService.appsLiveData,
        settingsService.menuAppsSizeLiveData,
        settingsService.menuAppsSpanLiveData
    ) { categories, apps, size, span ->

        val appsGroupByCategories = mutableListOf<AppsGroupByCategoryModel>()
        val sortedCategories = categories.sortedBy { it.position }
        val sortedApps = apps.sortedBy { it.position }

        sortedCategories.forEach { category ->
            val categoryApps = sortedApps.filter { it.categoryId == category.id && !it.isHidden }
            if (categoryApps.isNotEmpty()) {
                val appsGroupByCategory = appsGroupByCategoryMapper.map(
                    category,
                    categoryApps,
                    size,
                    span
                )
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