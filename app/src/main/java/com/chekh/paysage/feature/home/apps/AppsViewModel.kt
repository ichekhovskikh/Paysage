package com.chekh.paysage.feature.home.apps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.extension.*
import com.chekh.paysage.feature.home.AppRepository
import com.chekh.paysage.feature.home.AppRepositoryImpl
import com.chekh.paysage.feature.home.apps.model.AppsGroupByCategory
import com.chekh.paysage.feature.home.apps.model.ExpandableAppsGroupByCategory
import com.chekh.paysage.ui.viewmodel.BaseViewModel

class AppsViewModel : BaseViewModel<Unit>() {

    private val repository: AppRepository by lazy { AppRepositoryImpl() }

    private val expandedCategoryIds = mutableSetOf<String?>()
    private val scrollCategoriesOffsets: MutableMap<String, Int> = hashMapOf()

    private val expandTrigger = MutableLiveData<Unit>()

    private val categoriesLiveData = trigger
        .switchMap { repository.getAppsGroupByCategories() }
        .sortedBy { it.category?.position }

    val appsGroupByCategories = expandTrigger
        .switchMap {
            categoriesLiveData.foreachMap {
                ExpandableAppsGroupByCategory(it, isExpanded(it), scrollOffset(it))
            }
        }

    private val mutableScrollPosition = MutableLiveData<Int>()
    val scrollPosition: LiveData<Int> = mutableScrollPosition

    override fun init(trigger: Unit) {
        super.init(trigger)
        expandTrigger.postValue(Unit)
    }

    fun scrollCategoryOffset(scrollOffset: Int, categoryId: String) {
        scrollCategoriesOffsets[categoryId] = scrollOffset
        expandTrigger.postValue(Unit)
    }

    fun toggleCategory(position: Int, categoryId: String) {
        mutableScrollPosition.postValue(position)
        expandedCategoryIds.toggle(categoryId)
        scrollCategoriesOffsets.remove(categoryId)
        expandTrigger.postValue(Unit)
    }

    fun collapseAll() {
        mutableScrollPosition.postValue(0)
        expandedCategoryIds.clear()
        scrollCategoriesOffsets.clear()
        expandTrigger.postValue(Unit)
    }

    private fun isExpanded(data: AppsGroupByCategory?): Boolean {
        return expandedCategoryIds.contains(data?.category?.id)
    }

    private fun scrollOffset(data: AppsGroupByCategory?): Int {
        return scrollCategoriesOffsets[data?.category?.id] ?: 0
    }

}