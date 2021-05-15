package com.chekh.paysage.feature.main.presentation.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.extension.*
import androidx.hilt.lifecycle.ViewModelInject
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.main.domain.usecase.app.GetAppsGroupByCategoriesScenario
import com.chekh.paysage.feature.main.domain.usecase.settings.GetBoardAppSettingsUseCase
import com.chekh.paysage.feature.main.domain.usecase.settings.GetDockAppSettingsUseCase
import com.chekh.paysage.feature.main.domain.usecase.app.GetDockAppsUseCase
import com.chekh.paysage.feature.main.presentation.apps.mapper.AppGroupsModelMapper

class AppsViewModel @ViewModelInject constructor(
    private val getAppsGroupByCategoriesScenario: GetAppsGroupByCategoriesScenario,
    private val getBoardAppSettingsUseCase: GetBoardAppSettingsUseCase,
    private val getDockAppsUseCase: GetDockAppsUseCase,
    private val getDockAppSettingsUseCase: GetDockAppSettingsUseCase,
    private val appGroupsMapper: AppGroupsModelMapper
) : BaseViewModel<Unit>() {

    private val expandedCategoryIds = mutableSetOf<String?>()
    private val groupScrollOffsets: MutableMap<String, Int> = hashMapOf()

    private val expandTrigger = MutableLiveData<Unit>()

    val boardAppSettingsLiveData = trigger
        .switchMap { getBoardAppSettingsUseCase() }
        .distinctUntilChanged()

    val appGroupsLiveData = trigger
        .switchMap { getAppsGroupByCategoriesScenario() }
        .repeat(expandTrigger)
        .foreachMap {
            appGroupsMapper.map(it, isExpanded(it), scrollOffset(it))
        }
        .after(boardAppSettingsLiveData)
        .distinctUntilChanged()

    private val scrollPositionMutableLiveData = MutableLiveData<Int>()
    val scrollPositionLiveData: LiveData<Int> = scrollPositionMutableLiveData

    val dockAppSettingsLiveData: LiveData<AppSettingsModel> = trigger
        .switchMap { getDockAppSettingsUseCase() }
        .distinctUntilChanged()

    val dockAppsLiveData: LiveData<List<AppModel>> = dockAppSettingsLiveData
        .switchMap { getDockAppsUseCase(it.appColumnCount) }
        .distinctUntilChanged()

    fun onGroupScrollOffsetChanged(scrollOffset: Int, categoryId: String) {
        groupScrollOffsets[categoryId] = scrollOffset
        expandTrigger.value = Unit
    }

    fun toggleCategory(position: Int, categoryId: String) {
        scrollPositionMutableLiveData.value = position
        expandedCategoryIds.toggle(categoryId)
        groupScrollOffsets.remove(categoryId)
        expandTrigger.value = Unit
    }

    fun collapseAll() {
        scrollPositionMutableLiveData.value = 0
        expandedCategoryIds.clear()
        groupScrollOffsets.clear()
        expandTrigger.value = Unit
    }

    private fun isExpanded(data: AppsGroupByCategoryModel?): Boolean {
        return expandedCategoryIds.contains(data?.category?.id)
    }

    private fun scrollOffset(data: AppsGroupByCategoryModel?): Int {
        return groupScrollOffsets[data?.category?.id] ?: 0
    }
}
