package com.chekh.paysage.feature.main.presentation.desktop

import android.graphics.Rect
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.usecase.*
import com.chekh.paysage.feature.main.presentation.desktop.tools.DraggingDesktopWidgetSorter
import com.chekh.paysage.feature.main.presentation.desktop.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.presentation.desktop.mapper.DesktopWidgetFlowListItemMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.launch

class DesktopViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDesktopWidgetsUseCase: GetDesktopWidgetsUseCase,
    private val updateDesktopWidgetUseCase: UpdateDesktopWidgetUseCase,
    private val updateAllDesktopWidgetsUseCase: UpdateAllDesktopWidgetsUseCase,
    private val removeDesktopWidgetUseCase: RemoveDesktopWidgetUseCase,
    private val getDesktopGridSizeUseCase: GetDesktopGridSizeUseCase,
    private val getDockAppSizeUseCase: GetDockAppSizeUseCase,
    private val desktopWidgetModelFactory: DesktopWidgetModelFactory,
    private val draggingWidgetSorter: DraggingDesktopWidgetSorter,
    private val flowListItemMapper: DesktopWidgetFlowListItemMapper
) : BaseViewModel<Unit>() {

    private var desktopWidgets: List<DesktopWidgetModel>? = null

    private val draggingTrigger = MutableLiveData<Unit>()

    val desktopGridSizeLiveData = trigger
        .switchMap { getDesktopGridSizeUseCase() }
        .doNext { it?.let { draggingWidgetSorter.setDesktopGridSize(it) } }
        .distinctUntilChanged()

    val desktopWidgetsLiveData = trigger
        .switchMap { getDesktopWidgetsUseCase() }
        .doNext { desktopWidgets = it }
        .after(draggingTrigger, isRepeat = true)
        .map { draggingWidgetSorter.getSorted(it) }
        .foreachMap { flowListItemMapper.map(it) }
        .distinctUntilChanged()

    val dockAppSizeLiveData = trigger
        .switchMap { getDockAppSizeUseCase() }
        .distinctUntilChanged()

    override fun init(trigger: Unit) {
        super.init(trigger)
        draggingWidgetSorter.setOnSortOrderChangedListener {
            draggingTrigger.postValue(Unit)
        }
    }

    fun addDesktopWidget(desktopWidgetId: Int, widget: WidgetModel, bounds: Rect) {
        viewModelScope.launch(dispatcherProvider.back) {
            draggingWidgetSorter.setDraggingWidget(
                desktopWidgetModelFactory.create(desktopWidgetId.toString(), widget, bounds)
            )
            val sortedDesktopWidgets = draggingWidgetSorter.getSorted(desktopWidgets)
            draggingWidgetSorter.setDraggingWidget(null)
            updateAllDesktopWidgetsUseCase(sortedDesktopWidgets)
        }
    }

    fun removeDesktopWidget(desktopWidgetId: String) {
        viewModelScope.launch(dispatcherProvider.back) {
            removeDesktopWidgetUseCase(desktopWidgetId)
        }
    }

    fun setDraggingDesktopWidget(desktopWidgetId: String?, widget: WidgetModel?, bounds: Rect) {
        draggingWidgetSorter.setDraggingWidget(
            desktopWidgetModelFactory.create(desktopWidgetId, widget, bounds, isDragging = true)
        )
    }

    fun clearDraggingDesktopWidget() {
        draggingWidgetSorter.setDraggingWidget(null)
    }
}
