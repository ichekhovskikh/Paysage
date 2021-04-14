package com.chekh.paysage.feature.main.presentation.pager

import android.graphics.PointF
import android.graphics.Rect
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.extension.distinctUntilChanged
import com.chekh.paysage.core.extension.ignoreFirst
import com.chekh.paysage.core.extension.switchMap
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.usecase.page.AddDesktopPageUseCase
import com.chekh.paysage.feature.main.domain.usecase.page.GetDesktopPagesUseCase
import com.chekh.paysage.feature.main.domain.usecase.page.RemoveEmptyDesktopPagesUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.GetDesktopWidgetsUpdatesUseCase
import com.chekh.paysage.feature.main.presentation.pager.factory.DesktopPageModelFactory
import com.chekh.paysage.feature.main.presentation.pager.tools.DesktopPagerSwitcherDragHandler
import kotlinx.coroutines.launch

class DesktopPagerViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDesktopPagesUseCase: GetDesktopPagesUseCase,
    private val addDesktopPageUseCase: AddDesktopPageUseCase,
    private val removeEmptyDesktopPagesUseCase: RemoveEmptyDesktopPagesUseCase,
    private val getDesktopWidgetsUpdatesUseCase: GetDesktopWidgetsUpdatesUseCase,
    private val pageModelFactory: DesktopPageModelFactory,
    private val switcherDragHandler: DesktopPagerSwitcherDragHandler
) : BaseViewModel<Unit>() {

    val desktopWidgetsUpdatesLiveData = trigger
        .switchMap { getDesktopWidgetsUpdatesUseCase() }
        .ignoreFirst()

    val pagesLiveData = trigger
        .switchMap { getDesktopPagesUseCase() }
        .distinctUntilChanged()

    private val touchPageMutableLiveData = MutableLiveData<Int>()
    val touchPageLiveData: LiveData<Int> = touchPageMutableLiveData

    private val lastAvailablePosition: Int
        get() = pagesLiveData.value
            ?.maxByOrNull { it.position }
            ?.position
            ?.let { it + 1 }
            ?: 0

    override fun init(trigger: Unit) {
        super.init(trigger)
        switcherDragHandler.setOnTouchPageChanged {
            touchPageMutableLiveData.postValue(it)
        }
    }

    fun addLastDesktopPage() {
        viewModelScope.launch(dispatcherProvider.back) {
            addDesktopPageUseCase(pageModelFactory.create(lastAvailablePosition))
        }
    }

    fun removeEmptyDesktopPages() {
        viewModelScope.launch(dispatcherProvider.back) {
            removeEmptyDesktopPagesUseCase()
        }
    }

    fun handleDragTouch(touch: PointF, page: Int, pageBounds: Rect) {
        switcherDragHandler.handleDragTouch(touch, page, pageBounds)
    }

    fun stopHandleDragTouch() {
        switcherDragHandler.stopHandleDragTouch()
    }
}
