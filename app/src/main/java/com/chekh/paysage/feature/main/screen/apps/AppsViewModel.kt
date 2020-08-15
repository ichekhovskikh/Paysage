package com.chekh.paysage.feature.main.screen.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.extension.*
import com.chekh.paysage.feature.main.domain.model.DockAppsModel
import com.chekh.paysage.feature.main.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.main.domain.usecase.GetAppsGroupByCategoriesScenario
import com.chekh.paysage.feature.main.domain.usecase.GetDockAppsWithSettingsScenario
import com.chekh.paysage.feature.main.screen.apps.model.ExpandableAppsGroupByCategoryModel
import com.chekh.paysage.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class AppsViewModel @Inject constructor(
    private val getAppsGroupByCategoriesScenario: GetAppsGroupByCategoriesScenario,
    private val getDockAppsWithSettingsScenario: GetDockAppsWithSettingsScenario
) : BaseViewModel<Unit>() {

    private val expandedCategoryIds = mutableSetOf<String?>()
    private val scrollCategoriesOffsets: MutableMap<String, Int> = hashMapOf()

    private val expandTrigger = MutableLiveData<Unit>()

    val appsGroupByCategoriesLiveData = trigger
        .switchMap { getAppsGroupByCategoriesScenario() }
        .repeat(expandTrigger)
        .foreachMap {
            ExpandableAppsGroupByCategoryModel(it, isExpanded(it), scrollOffset(it))
        }
        .distinctUntilChanged()

    private val scrollPositionMutableLiveData = MutableLiveData<Int>()
    val scrollPositionLiveData: LiveData<Int> = scrollPositionMutableLiveData

    val dockAppsLiveData: LiveData<DockAppsModel> = trigger
        .switchMap { getDockAppsWithSettingsScenario() }

    override fun init(trigger: Unit) {
        super.init(trigger)
        expandTrigger.postValue(Unit)
    }

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