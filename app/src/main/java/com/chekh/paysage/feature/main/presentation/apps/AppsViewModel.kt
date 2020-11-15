package com.chekh.paysage.feature.main.presentation.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.extension.*
import androidx.hilt.lifecycle.ViewModelInject
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.main.domain.model.AppsModel
import com.chekh.paysage.feature.main.domain.usecase.GetAppsGroupByCategoriesScenario
import com.chekh.paysage.feature.main.domain.usecase.GetDockAppsWithSettingsScenario
import com.chekh.paysage.feature.main.presentation.apps.mapper.ExpandableAppsGroupByCategoryModelMapper

class AppsViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getAppsGroupByCategoriesScenario: GetAppsGroupByCategoriesScenario,
    private val getDockAppsWithSettingsScenario: GetDockAppsWithSettingsScenario,
    private val expandableAppsGroupByCategoryMapper: ExpandableAppsGroupByCategoryModelMapper
) : BaseViewModel<Unit>() {

    private val expandedCategoryIds = mutableSetOf<String?>()
    private val scrollCategoriesOffsets: MutableMap<String, Int> = hashMapOf()

    private val expandTrigger = MutableLiveData<Unit>()

    val appsGroupByCategoriesLiveData = trigger
        .switchMap { getAppsGroupByCategoriesScenario().asConflateLiveData(dispatcherProvider.io) }
        .repeat(expandTrigger)
        .foreachMap {
            expandableAppsGroupByCategoryMapper.map(it, isExpanded(it), scrollOffset(it))
        }
        .distinctUntilChanged()

    private val scrollPositionMutableLiveData = MutableLiveData<Int>()
    val scrollPositionLiveData: LiveData<Int> = scrollPositionMutableLiveData

    val dockAppsLiveData: LiveData<AppsModel> = trigger
        .switchMap { getDockAppsWithSettingsScenario().asConflateLiveData(dispatcherProvider.io) }
        .distinctUntilChanged()

    fun scrollCategoryOffset(scrollOffset: Int, categoryId: String) {
        scrollCategoriesOffsets[categoryId] = scrollOffset
        expandTrigger.postValue(Unit)
    }

    fun toggleCategory(position: Int, categoryId: String) {
        scrollPositionMutableLiveData.postValue(position)
        expandedCategoryIds.toggle(categoryId)
        scrollCategoriesOffsets.remove(categoryId)
        expandTrigger.postValue(Unit)
    }

    fun collapseAll() {
        scrollPositionMutableLiveData.postValue(0)
        expandedCategoryIds.clear()
        scrollCategoriesOffsets.clear()
        expandTrigger.postValue(Unit)
    }

    private fun isExpanded(data: AppsGroupByCategoryModel?): Boolean {
        return expandedCategoryIds.contains(data?.category?.id)
    }

    private fun scrollOffset(data: AppsGroupByCategoryModel?): Int {
        return scrollCategoriesOffsets[data?.category?.id] ?: 0
    }
}
