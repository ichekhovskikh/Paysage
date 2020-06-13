package com.chekh.paysage.feature.home.screen.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.extension.*
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel
import com.chekh.paysage.feature.home.domain.usecase.AppsGroupByCategoriesUseCase
import com.chekh.paysage.feature.home.screen.apps.model.ExpandableAppsGroupByCategoryModel
import com.chekh.paysage.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class AppsViewModel @Inject constructor(
    private val appsGroupByCategoriesUseCase: AppsGroupByCategoriesUseCase
) : BaseViewModel<Unit>() {

    private val expandedCategoryIds = mutableSetOf<String?>()
    private val scrollCategoriesOffsets: MutableMap<String, Int> = hashMapOf()

    private val expandTrigger = MutableLiveData<Unit>()

    private val categoriesMutableLiveData = trigger.switchMap { appsGroupByCategoriesUseCase() }

    val appsGroupByCategoriesLiveData = expandTrigger.switchMap {
        categoriesMutableLiveData.foreachMap {
            ExpandableAppsGroupByCategoryModel(it, isExpanded(it), scrollOffset(it))
        }
    }

    private val scrollPositionMutableLiveData = MutableLiveData<Int>()
    val scrollPositionLiveData: LiveData<Int> = scrollPositionMutableLiveData

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