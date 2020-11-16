package com.chekh.paysage.feature.widget.presentation.widgetboard

import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.extension.*
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.tools.onAppsChanged
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByPackageModel
import com.chekh.paysage.feature.widget.domain.usecase.GetSortedWidgetsGroupByPackageScenario
import com.chekh.paysage.feature.widget.domain.usecase.PullBoardWidgetsUseCase
import com.chekh.paysage.feature.widget.domain.usecase.StartObserveBoardWidgetUpdatesUseCase
import com.chekh.paysage.feature.widget.domain.usecase.StopObserveBoardWidgetUpdatesUseCase
import com.chekh.paysage.feature.widget.presentation.widgetboard.mapper.ScrollableWidgetsGroupByPackageModelMapper
import kotlinx.coroutines.launch

class WidgetBoardViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getSortedWidgetsGroupByPackageScenario: GetSortedWidgetsGroupByPackageScenario,
    private val pullBoardWidgetsUseCase: PullBoardWidgetsUseCase,
    private val startObserveBoardWidgetUpdatesUseCase: StartObserveBoardWidgetUpdatesUseCase,
    private val stopObserveBoardWidgetUpdatesUseCase: StopObserveBoardWidgetUpdatesUseCase,
    private val scrollableWidgetsGroupByPackageMapper: ScrollableWidgetsGroupByPackageModelMapper
) : BaseViewModel<Unit>() {

    private val scrollCategoriesOffsets: MutableMap<String, Int> = hashMapOf()

    private val scrollTrigger = MutableLiveData<Unit>()

    val widgetsGroupByPackageLiveData = trigger
        .switchMap {
            getSortedWidgetsGroupByPackageScenario().asConflateLiveData(dispatcherProvider.background)
        }
        .repeat(scrollTrigger)
        .foreachMap {
            scrollableWidgetsGroupByPackageMapper.map(it, scrollOffset(it))
        }
        .distinctUntilChanged()

    private val widgetsChangedCallback = onAppsChanged { _, _ -> pullBoardWidgets() }

    override fun init(trigger: Unit) {
        startObserveWidgetUpdates()
        pullBoardWidgets()
        super.init(trigger)
    }

    private fun pullBoardWidgets() {
        viewModelScope.launch(dispatcherProvider.background) {
            pullBoardWidgetsUseCase()
        }
    }

    private fun startObserveWidgetUpdates() {
        viewModelScope.launch(dispatcherProvider.main) {
            startObserveBoardWidgetUpdatesUseCase(widgetsChangedCallback)
        }
    }

    private fun stopObserveWidgetUpdates() {
        viewModelScope.launch(dispatcherProvider.main) {
            stopObserveBoardWidgetUpdatesUseCase(widgetsChangedCallback)
        }
    }

    fun scrollCategoryOffset(scrollOffset: Int, widgetAppId: String) {
        scrollCategoriesOffsets[widgetAppId] = scrollOffset
        scrollTrigger.postValue(Unit)
    }

    private fun scrollOffset(data: WidgetsGroupByPackageModel?): Int {
        return scrollCategoriesOffsets[data?.widgetApp?.id] ?: 0
    }

    override fun onCleared() {
        stopObserveWidgetUpdates()
        super.onCleared()
    }
}
