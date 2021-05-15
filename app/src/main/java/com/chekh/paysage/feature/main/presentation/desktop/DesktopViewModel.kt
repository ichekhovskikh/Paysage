package com.chekh.paysage.feature.main.presentation.desktop

import android.graphics.Rect
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
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
import com.chekh.paysage.feature.main.presentation.desktop.tools.sorted
import com.chekh.paysage.feature.main.presentation.desktop.factory.DesktopWidgetModelFactory
import com.chekh.paysage.feature.main.presentation.desktop.mapper.DesktopWidgetFlowListItemMapper
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
import kotlinx.coroutines.launch

class DesktopViewModel @ViewModelInject constructor(
    getDockAppSizeScenario: GetDockAppSizeScenario,
    getDesktopGridSizeUseCase: GetDesktopGridSizeUseCase,
    private val getDesktopWidgetsByPageUseCase: GetDesktopWidgetsByPageUseCase,
    private val updateDesktopWidgetUseCase: UpdateDesktopWidgetUseCase,
    private val updateDesktopWidgetsByPageUseCase: UpdateDesktopWidgetsByPageUseCase,
    private val removeDesktopWidgetUseCase: RemoveDesktopWidgetUseCase,
    private val desktopWidgetModelFactory: DesktopWidgetModelFactory,
    private val flowListItemMapper: DesktopWidgetFlowListItemMapper
) : BaseViewModel<Unit>() {

    private var sortedDesktopWidgetsCache = mutableMapOf<Long, List<DesktopWidgetModel>?>()

    private val dragDesktopWidgetLiveData = MutableLiveData<DesktopWidgetModel?>()

    val desktopGridSizeLiveData = getDesktopGridSizeUseCase()
        .distinctUntilChanged()

    val dockAppSizeLiveData = getDockAppSizeScenario()
        .distinctUntilChanged()

    private val onFailApplyChangesMutableLiveData = MutableLiveData<Unit>()
    val onFailApplyChangesLiveData: LiveData<Unit> = onFailApplyChangesMutableLiveData

    fun getDesktopWidgetsLiveData(pageId: Long) = getDesktopWidgetsByPageUseCase(pageId)
        .after(desktopGridSizeLiveData, isRepeat = true)
        .repeat(dragDesktopWidgetLiveData)
        .map {
            it?.sorted(
                pageId = pageId,
                desktopSize = desktopGridSizeLiveData.value,
                dragWidget = dragDesktopWidgetLiveData.value
            )
        }
        .doNext { sortedDesktopWidgetsCache[pageId] = it }
        .filterNotNull()
        .distinctUntilChanged()
        .foreachMap(flowListItemMapper::map)

    fun onApplyDragChanges() {
        viewModelScope.launch(back) {
            val pageId = dragDesktopWidgetLiveData.value?.pageId
            if (pageId == null) {
                onFailApplyChangesMutableLiveData.postValue(Unit)
                return@launch
            }
            val sortedDesktopWidgets = sortedDesktopWidgetsCache[pageId]
            dragDesktopWidgetLiveData.postValue(null)
            if (sortedDesktopWidgets == null) {
                onFailApplyChangesMutableLiveData.postValue(Unit)
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
        dragDesktopWidgetLiveData.value = desktopWidgetModelFactory
            .create(widget, pageId, bounds)
    }

    fun onDragCancel() {
        dragDesktopWidgetLiveData.value = null
    }

    fun onPageChanged(pageId: Long) {
        dragDesktopWidgetLiveData
            .change { it?.copy(pageId = pageId) }
    }

    fun attachIdentifierToDragWidget(widgetId: Int) {
        dragDesktopWidgetLiveData
            .change { it?.copy(id = widgetId.toString()) }
    }
}
