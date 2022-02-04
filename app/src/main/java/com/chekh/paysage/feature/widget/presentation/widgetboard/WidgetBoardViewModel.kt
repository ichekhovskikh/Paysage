package com.chekh.paysage.feature.widget.presentation.widgetboard

import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByAppModel
import com.chekh.paysage.feature.widget.domain.usecase.GetSortedWidgetsGroupByAppScenario
import com.chekh.paysage.feature.widget.presentation.widgetboard.mapper.WidgetGroupModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WidgetBoardViewModel @Inject constructor(
    private val getSortedWidgetsGroupByAppScenario: GetSortedWidgetsGroupByAppScenario,
    private val widgetGroupMapper: WidgetGroupModelMapper
) : BaseViewModel<Unit>() {

    private val groupScrollOffsets: MutableMap<String, Int> = hashMapOf()

    private val scrollTrigger = MutableLiveData<Unit>()

    val widgetGroups = trigger
        .switchMap { getSortedWidgetsGroupByAppScenario() }
        .repeat(scrollTrigger)
        .foreachMap {
            widgetGroupMapper.map(it, scrollOffset(it))
        }
        .distinctUntilChanged()

    fun onGroupScrollOffsetChanged(scrollOffset: Int, widgetAppId: String) {
        groupScrollOffsets[widgetAppId] = scrollOffset
        scrollTrigger.value = Unit
    }

    private fun scrollOffset(data: WidgetsGroupByAppModel?): Int {
        return groupScrollOffsets[data?.app?.id] ?: 0
    }
}
