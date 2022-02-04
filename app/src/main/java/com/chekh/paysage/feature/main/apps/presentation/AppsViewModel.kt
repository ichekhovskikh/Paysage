package com.chekh.paysage.feature.main.apps.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.common.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.main.common.domain.usecase.app.GetAppsGroupByCategoriesScenario
import com.chekh.paysage.feature.main.common.domain.usecase.settings.GetBoardAppSettingsUseCase
import com.chekh.paysage.feature.main.apps.presentation.mapper.AppGroupsModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppsViewModel @Inject constructor(
    private val getAppsGroupByCategoriesScenario: GetAppsGroupByCategoriesScenario,
    private val getBoardAppSettingsUseCase: GetBoardAppSettingsUseCase,
    private val appGroupsMapper: AppGroupsModelMapper
) : BaseViewModel<Unit>() {

    private val expandedCategoryIds = mutableSetOf<String?>()
    private val groupScrollOffsets: MutableMap<String, Int> = hashMapOf()

    private val expandTrigger = MutableLiveData<Unit>()

    val boardAppSettings = trigger
        .switchMap { getBoardAppSettingsUseCase() }
        .distinctUntilChanged()

    val appGroups = trigger
        .switchMap { getAppsGroupByCategoriesScenario() }
        .repeat(expandTrigger)
        .foreachMap {
            appGroupsMapper.map(it, isExpanded(it), scrollOffset(it))
        }
        .after(boardAppSettings)
        .distinctUntilChanged()

    private val scrollPositionMutable = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> = scrollPositionMutable

    fun onGroupScrollOffsetChanged(scrollOffset: Int, categoryId: String) {
        groupScrollOffsets[categoryId] = scrollOffset
        expandTrigger.value = Unit
    }

    fun toggleCategory(position: Int, categoryId: String) {
        scrollPositionMutable.value = position
        expandedCategoryIds.toggle(categoryId)
        groupScrollOffsets.remove(categoryId)
        expandTrigger.value = Unit
    }

    fun collapseAll() {
        scrollPositionMutable.value = 0
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
