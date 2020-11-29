package com.chekh.paysage.feature.main.presentation.desktop

import android.graphics.RectF
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.extension.distinctUntilChanged
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.core.extension.switchMap
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.usecase.*
import com.chekh.paysage.feature.main.presentation.desktop.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.presentation.desktop.mapper.DesktopWidgetFlowListItemMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.launch

class DesktopViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDesktopWidgetsUseCase: GetDesktopWidgetsUseCase,
    private val updateDesktopWidgetUseCase: UpdateDesktopWidgetUseCase,
    private val addDesktopWidgetUseCase: AddDesktopWidgetUseCase,
    private val removeDesktopWidgetUseCase: RemoveDesktopWidgetUseCase,
    private val getDesktopGridSpanUseCase: GetDesktopGridSpanUseCase,
    private val desktopWidgetModelFactory: DesktopWidgetModelFactory,
    private val flowListItemMapper: DesktopWidgetFlowListItemMapper
) : BaseViewModel<Unit>() {

    val desktopGridSpan = trigger
        .switchMap { getDesktopGridSpanUseCase() }
        .distinctUntilChanged()

    val desktopWidgets = trigger
        .switchMap { getDesktopWidgetsUseCase() }
        .foreachMap { flowListItemMapper.map(it) }
        .distinctUntilChanged()

    fun setDesktopWidgetBounds(desktopWidget: DesktopWidgetModel, bounds: RectF) {
        // TODO reorder
        viewModelScope.launch(dispatcherProvider.back) {
            val newDesktopWidget = desktopWidget.copy(
                x = bounds.left.toInt(),
                y = bounds.top.toInt(),
                height = bounds.height().toInt(),
                width = bounds.width().toInt()
            )
            updateDesktopWidgetUseCase(newDesktopWidget)
        }
    }

    fun addDesktopWidget(desktopWidgetId: Int, widget: WidgetModel, bounds: RectF) {
        // TODO reorder
        viewModelScope.launch(dispatcherProvider.back) {
            val desktopWidget = desktopWidgetModelFactory.create(desktopWidgetId, widget, bounds)
            addDesktopWidgetUseCase(desktopWidget)
        }
    }

    fun removeDesktopWidget(desktopWidgetId: String) {
        viewModelScope.launch(dispatcherProvider.back) {
            removeDesktopWidgetUseCase(desktopWidgetId)
        }
    }
}
