package com.chekh.paysage.feature.main.presentation.desktop

import android.graphics.Rect
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.usecase.settings.GetDesktopGridSizeUseCase
import com.chekh.paysage.feature.main.domain.usecase.settings.GetDockAppSizeScenario
import com.chekh.paysage.feature.main.domain.usecase.widget.GetDesktopWidgetsUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.RemoveDesktopWidgetUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.UpdateDesktopWidgetsByPageUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.UpdateDesktopWidgetUseCase
import com.chekh.paysage.feature.main.presentation.desktop.tools.DraggingDesktopWidgetSorter
import com.chekh.paysage.feature.main.presentation.desktop.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.presentation.desktop.mapper.DesktopWidgetFlowListItemMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.launch

class DesktopViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDesktopWidgetsUseCase: GetDesktopWidgetsUseCase,
    private val updateDesktopWidgetUseCase: UpdateDesktopWidgetUseCase,
    private val updateDesktopWidgetsByPageUseCase: UpdateDesktopWidgetsByPageUseCase,
    private val removeDesktopWidgetUseCase: RemoveDesktopWidgetUseCase,
    private val getDesktopGridSizeUseCase: GetDesktopGridSizeUseCase,
    private val getDockAppSizeScenario: GetDockAppSizeScenario,
    private val desktopWidgetModelFactory: DesktopWidgetModelFactory,
    private val draggingWidgetSorter: DraggingDesktopWidgetSorter,
    private val flowListItemMapper: DesktopWidgetFlowListItemMapper
) : BaseViewModel<Long>() {

    private var desktopWidgets: List<DesktopWidgetModel>? = null

    private val draggingTrigger = MutableLiveData<Unit>()

    private val pageId get() = trigger.value

    val desktopGridSizeLiveData = trigger
        .switchMap { getDesktopGridSizeUseCase() }
        .doNext { it?.let { draggingWidgetSorter.setDesktopGridSize(it) } }
        .distinctUntilChanged()

    val desktopWidgetsLiveData = trigger
        .switchMap { getDesktopWidgetsUseCase(it) }
        .doNext { desktopWidgets = it }
        .after(draggingTrigger, isRepeat = true)
        .map(draggingWidgetSorter::getSorted)
        .foreachMap(flowListItemMapper::map)
        .distinctUntilChanged()

    val dockAppSizeLiveData = trigger
        .switchMap { getDockAppSizeScenario() }
        .distinctUntilChanged()

    override fun init(trigger: Long) {
        super.init(trigger)
        draggingWidgetSorter.setOnSortOrderChangedListener {
            draggingTrigger.postValue(Unit)
        }
    }

    fun addDesktopWidget(desktopWidgetId: Int, widget: WidgetModel, bounds: Rect) {
        val pageId = pageId ?: return
        viewModelScope.launch(dispatcherProvider.back) {
            draggingWidgetSorter.setDraggingWidget(
                desktopWidgetModelFactory.create(desktopWidgetId.toString(), widget, pageId, bounds)
            )
            val sortedDesktopWidgets = draggingWidgetSorter.getSorted(desktopWidgets)
            draggingWidgetSorter.setDraggingWidget(null)
            updateDesktopWidgetsByPageUseCase(pageId, sortedDesktopWidgets)
        }
    }

    fun removeDesktopWidget(desktopWidgetId: String) {
        viewModelScope.launch(dispatcherProvider.back) {
            removeDesktopWidgetUseCase(desktopWidgetId)
        }
    }

    fun setDraggingDesktopWidget(desktopWidgetId: String?, widget: WidgetModel?, bounds: Rect) {
        val pageId = pageId ?: return
        draggingWidgetSorter.setDraggingWidget(
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
        draggingWidgetSorter.setDraggingWidget(null)
    }
}
