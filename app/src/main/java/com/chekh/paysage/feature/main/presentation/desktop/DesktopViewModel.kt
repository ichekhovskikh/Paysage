package com.chekh.paysage.feature.main.presentation.desktop

import android.graphics.Rect
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.provider.back
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.usecase.settings.GetDesktopGridSizeUseCase
import com.chekh.paysage.feature.main.domain.usecase.settings.GetDockAppSizeScenario
import com.chekh.paysage.feature.main.domain.usecase.widget.GetDesktopWidgetsByPageUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.RemoveDesktopWidgetUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.UpdateDesktopWidgetsByPageUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.UpdateDesktopWidgetUseCase
import com.chekh.paysage.feature.main.presentation.desktop.tools.DraggingDesktopWidgetSorter
import com.chekh.paysage.feature.main.presentation.desktop.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.presentation.desktop.mapper.DesktopWidgetFlowListItemMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.launch

class DesktopViewModel @ViewModelInject constructor(
    private val getDesktopWidgetsByPageUseCase: GetDesktopWidgetsByPageUseCase,
    private val updateDesktopWidgetUseCase: UpdateDesktopWidgetUseCase,
    private val updateDesktopWidgetsByPageUseCase: UpdateDesktopWidgetsByPageUseCase,
    private val removeDesktopWidgetUseCase: RemoveDesktopWidgetUseCase,
    private val getDesktopGridSizeUseCase: GetDesktopGridSizeUseCase,
    private val getDockAppSizeScenario: GetDockAppSizeScenario,
    private val desktopWidgetModelFactory: DesktopWidgetModelFactory,
    private val draggingWidgetSorter: DraggingDesktopWidgetSorter,
    private val flowListItemMapper: DesktopWidgetFlowListItemMapper
) : BaseViewModel<Unit>() {

    private var cachedDesktopWidgets: MutableMap<Long, List<DesktopWidgetModel>?> = mutableMapOf()

    private val draggingDesktopWidgetLiveData = MutableLiveData<DesktopWidgetModel?>()

    val desktopGridSizeLiveData = getDesktopGridSizeUseCase()
        .distinctUntilChanged()

    val dockAppSizeLiveData = getDockAppSizeScenario()
        .distinctUntilChanged()

    fun getDesktopWidgetsLiveData(pageId: Long) = getDesktopWidgetsByPageUseCase(pageId)
        .doNext { cachedDesktopWidgets[pageId] = it }
        .after(desktopGridSizeLiveData, isRepeat = true)
        .repeat(draggingDesktopWidgetLiveData)
        .map {
            draggingWidgetSorter.getSorted(
                pageId = pageId,
                desktopSize = desktopGridSizeLiveData.value,
                draggingWidget = draggingDesktopWidgetLiveData.value,
                unsortedWidgets = it
            )
        }
        .distinctUntilChanged()
        .foreachMap(flowListItemMapper::map)

    fun addDesktopWidget(pageId: Long, desktopWidgetId: Int, widget: WidgetModel, bounds: Rect) {
        viewModelScope.launch(back) {
            val sortedDesktopWidgets = draggingWidgetSorter.getSorted(
                pageId = pageId,
                desktopSize = desktopGridSizeLiveData.value,
                draggingWidget = desktopWidgetModelFactory.create(
                    desktopWidgetId.toString(),
                    widget,
                    pageId,
                    bounds
                ),
                unsortedWidgets = cachedDesktopWidgets[pageId]
            )
            clearDraggingDesktopWidget()
            updateDesktopWidgetsByPageUseCase(pageId, sortedDesktopWidgets)
        }
    }

    fun removeDesktopWidget(desktopWidgetId: String) {
        viewModelScope.launch(back) {
            removeDesktopWidgetUseCase(desktopWidgetId)
        }
    }

    fun setDraggingDesktopWidget(
        pageId: Long,
        desktopWidgetId: String?,
        widget: WidgetModel?,
        bounds: Rect
    ) {
        draggingDesktopWidgetLiveData.postValue(
            desktopWidgetModelFactory.create(
                desktopWidgetId,
                widget,
                pageId,
                bounds,
                isDragging = true
            )
        )
    }

    fun clearDraggingDesktopWidget() {
        draggingDesktopWidgetLiveData.postValue(null)
    }

    fun onPageChanged(pageId: Long) {
        draggingDesktopWidgetLiveData.value ?: return
        draggingDesktopWidgetLiveData.postValue(
            draggingDesktopWidgetLiveData.value?.copy(pageId = pageId)
        )
    }
}
