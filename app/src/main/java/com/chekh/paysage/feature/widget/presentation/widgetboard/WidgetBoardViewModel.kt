package com.chekh.paysage.feature.widget.presentation.widgetboard

import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.extension.*
import androidx.hilt.lifecycle.ViewModelInject
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByPackageModel
import com.chekh.paysage.feature.widget.domain.usecase.GetSortedWidgetsGroupByPackageScenario
import com.chekh.paysage.feature.widget.presentation.widgetboard.mapper.ScrollableWidgetsGroupByPackageModelMapper

class WidgetBoardViewModel @ViewModelInject constructor(
    private val getSortedWidgetsGroupByPackageScenario: GetSortedWidgetsGroupByPackageScenario,
    private val scrollableWidgetsGroupByPackageMapper: ScrollableWidgetsGroupByPackageModelMapper
) : BaseViewModel<Unit>() {

    private val scrollCategoriesOffsets: MutableMap<String, Int> = hashMapOf()

    private val scrollTrigger = MutableLiveData<Unit>()

    val widgetsGroupByPackageLiveData = trigger
        .switchMap { getSortedWidgetsGroupByPackageScenario() }
        .repeat(scrollTrigger)
        .foreachMap {
            scrollableWidgetsGroupByPackageMapper.map(it, scrollOffset(it))
        }
        .distinctUntilChanged()

    fun scrollCategoryOffset(scrollOffset: Int, widgetAppId: String) {
        scrollCategoriesOffsets[widgetAppId] = scrollOffset
        scrollTrigger.postValue(Unit)
    }

    private fun scrollOffset(data: WidgetsGroupByPackageModel?): Int {
        return scrollCategoriesOffsets[data?.widgetApp?.id] ?: 0
    }
}
