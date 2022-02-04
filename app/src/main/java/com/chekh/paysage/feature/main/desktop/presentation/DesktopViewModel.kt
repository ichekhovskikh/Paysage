package com.chekh.paysage.feature.main.desktop.presentation

import android.graphics.Rect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.provider.back
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.common.domain.usecase.settings.GetDesktopGridSizeUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.widget.GetDesktopWidgetsByPageUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.widget.RemoveDesktopWidgetUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.widget.UpdateDesktopWidgetsByPageUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.widget.UpdateDesktopWidgetUseCase
import com.chekh.paysage.feature.main.desktop.presentation.tools.sorted
import com.chekh.paysage.feature.main.desktop.presentation.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.desktop.presentation.mapper.DesktopWidgetFlowListItemMapper
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesktopViewModel @Inject constructor(
    getDesktopGridSizeUseCase: GetDesktopGridSizeUseCase,
    private val getDesktopWidgetsByPageUseCase: GetDesktopWidgetsByPageUseCase,
    private val updateDesktopWidgetUseCase: UpdateDesktopWidgetUseCase,
    private val updateDesktopWidgetsByPageUseCase: UpdateDesktopWidgetsByPageUseCase,
    private val removeDesktopWidgetUseCase: RemoveDesktopWidgetUseCase,
    private val desktopWidgetModelFactory: DesktopWidgetModelFactory,
    private val flowListItemMapper: DesktopWidgetFlowListItemMapper
) : BaseViewModel<Unit>() {

    private var sortedDesktopWidgetsCache = mutableMapOf<Long, List<DesktopWidgetModel>?>()

    private val dragDesktopWidget = MutableLiveData<DesktopWidgetModel?>()

    val desktopGridSize = getDesktopGridSizeUseCase()
        .distinctUntilChanged()

    private val onFailApplyChangesMutable = MutableLiveData<Unit>()
    val onFailApplyChanges: LiveData<Unit> = onFailApplyChangesMutable

    fun getDesktopWidgets(pageId: Long) = getDesktopWidgetsByPageUseCase(pageId)
        .after(desktopGridSize, isRepeat = true)
        .repeat(dragDesktopWidget)
        .map {
            it?.sorted(
                pageId = pageId,
                desktopSize = desktopGridSize.value,
                dragWidget = dragDesktopWidget.value
            )
        }
        .doNext { sortedDesktopWidgetsCache[pageId] = it }
        .filterNotNull()
        .distinctUntilChanged()
        .foreachMap(flowListItemMapper::map)

    fun onApplyDragChanges() {
        viewModelScope.launch(back) {
            val pageId = dragDesktopWidget.value?.pageId
            if (pageId == null) {
                onFailApplyChangesMutable.postValue(Unit)
                return@launch
            }
            val sortedDesktopWidgets = sortedDesktopWidgetsCache[pageId]
            dragDesktopWidget.postValue(null)
            if (sortedDesktopWidgets == null) {
                onFailApplyChangesMutable.postValue(Unit)
                return@launch
            }
            updateDesktopWidgetsByPageUseCase(pageId, sortedDesktopWidgets)
        }
    }

    fun onRemoveWidget(desktopWidgetId: String) {
        viewModelScope.launch(back) {
            removeDesktopWidgetUseCase(desktopWidgetId)
        }
    }

    fun onDragMove(pageId: Long, widget: WidgetClipData, bounds: Rect) {
        dragDesktopWidget.value = desktopWidgetModelFactory
            .create(widget, pageId, bounds)
    }

    fun onDragCancel() {
        dragDesktopWidget.value = null
    }

    fun onPageChanged(pageId: Long) {
        dragDesktopWidget
            .change { it?.copy(pageId = pageId) }
    }

    fun attachIdentifierToDragWidget(widgetId: Int) {
        dragDesktopWidget
            .change { it?.copy(id = widgetId.toString()) }
    }
}
