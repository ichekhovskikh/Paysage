package com.chekh.paysage.feature.main.domain.usecase.app

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.zip
import com.chekh.paysage.feature.main.domain.mapper.AppsGroupByCategoryModelMapper
import com.chekh.paysage.feature.main.domain.model.AppsGroupByCategoryModel
import javax.inject.Inject

class GetAppsGroupByCategoriesScenario @Inject constructor(
    private val getAppCategoriesUseCase: GetAppCategoriesUseCase,
    private val getBoardAppsUseCase: GetBoardAppsUseCase,
    private val appsGroupByCategoryMapper: AppsGroupByCategoryModelMapper
) {

    operator fun invoke(): LiveData<List<AppsGroupByCategoryModel>> = zip(
        getAppCategoriesUseCase(),
        getBoardAppsUseCase()
    ) { categories, apps ->

        val appsGroupByCategories = mutableListOf<AppsGroupByCategoryModel>()
        val sortedCategories = categories.sortedBy { it.position }
        val sortedApps = apps.sortedBy { it.position }

        sortedCategories.forEach { category ->
            val categoryApps = sortedApps.filter { it.categoryId == category.id && !it.isHidden }
            if (categoryApps.isNotEmpty()) {
                val appsGroupByCategory = appsGroupByCategoryMapper.map(
                    category,
                    categoryApps
                )
                appsGroupByCategories.add(appsGroupByCategory)
            }
        }
        appsGroupByCategories
    }
}
