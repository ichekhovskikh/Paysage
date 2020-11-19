package com.chekh.paysage.feature.main.presentation.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.extension.*
import androidx.hilt.lifecycle.ViewModelInject
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.main.domain.model.AppsModel
import com.chekh.paysage.feature.main.domain.usecase.GetAppsGroupByCategoriesScenario
import com.chekh.paysage.feature.main.domain.usecase.GetDockAppsWithSettingsScenario
import com.chekh.paysage.feature.main.presentation.apps.mapper.AppGroupsModelMapper

class AppsViewModel @ViewModelInject constructor(
    private val getAppsGroupByCategoriesScenario: GetAppsGroupByCategoriesScenario,
    private val getDockAppsWithSettingsScenario: GetDockAppsWithSettingsScenario,
    private val appGroupsMapper: AppGroupsModelMapper
) : BaseViewModel<Unit>() {

    private val expandedCategoryIds = mutableSetOf<String?>()
    private val groupScrollOffsets: MutableMap<String, Int> = hashMapOf()

    private val expandTrigger = MutableLiveData<Unit>()

    val appGroupsLiveData = trigger
        .switchMap { getAppsGroupByCategoriesScenario() }
        .repeat(expandTrigger)
        .foreachMap {
            appGroupsMapper.map(it, isExpanded(it), scrollOffset(it))
        }
        .distinctUntilChanged()

    private val scrollPositionMutableLiveData = MutableLiveData<Int>()
    val scrollPositionLiveData: LiveData<Int> = scrollPositionMutableLiveData

    val dockAppsLiveData: LiveData<AppsModel> = trigger
        .switchMap { getDockAppsWithSettingsScenario() }

    fun onGroupScrollOffsetChanged(scrollOffset: Int, categoryId: String) {
        groupScrollOffsets[categoryId] = scrollOffset
        expandTrigger.postValue(Unit)
    }

    fun toggleCategory(position: Int, categoryId: String) {
        scrollPositionMutableLiveData.postValue(position)
        expandedCategoryIds.toggle(categoryId)
        groupScrollOffsets.remove(categoryId)
        expandTrigger.postValue(Unit)
    }

    fun collapseAll() {
        scrollPositionMutableLiveData.postValue(0)
        expandedCategoryIds.clear()
        groupScrollOffsets.clear()
        expandTrigger.postValue(Unit)
    }

    private fun isExpanded(data: AppsGroupByCategoryModel?): Boolean {
        return expandedCategoryIds.contains(data?.category?.id)
    }

    private fun scrollOffset(data: AppsGroupByCategoryModel?): Int {
        return groupScrollOffsets[data?.category?.id] ?: 0
    }
}
