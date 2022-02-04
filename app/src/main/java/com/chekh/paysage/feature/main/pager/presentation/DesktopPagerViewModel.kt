package com.chekh.paysage.feature.main.pager.presentation

import android.graphics.PointF
import android.graphics.Rect
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.extension.distinctUntilChanged
import com.chekh.paysage.core.extension.switchMap
import com.chekh.paysage.core.provider.back
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.common.domain.usecase.page.AddDesktopPageUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.page.GetDesktopPagesUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.page.RemoveDesktopPageByPositionUseCase
import com.chekh.paysage.feature.main.pager.presentation.factory.DesktopPageModelFactory
import com.chekh.paysage.feature.main.pager.presentation.tools.DesktopPagerSwitcherDragHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DesktopPagerViewModel @Inject constructor(
    private val getDesktopPagesUseCase: GetDesktopPagesUseCase,
    private val addDesktopPageUseCase: AddDesktopPageUseCase,
    private val removeDesktopPageByPositionUseCase: RemoveDesktopPageByPositionUseCase,
    private val pageModelFactory: DesktopPageModelFactory,
    private val switcherDragHandler: DesktopPagerSwitcherDragHandler
) : BaseViewModel<Unit>() {

    val pages = trigger
        .switchMap { getDesktopPagesUseCase() }
        .distinctUntilChanged()

    private val touchPageMutable = MutableLiveData<Int>()
    val touchPage: LiveData<Int> = touchPageMutable

    private val lastPagePosition: Int
        get() = pages.value
            ?.maxByOrNull { it.position }
            ?.position
            ?: 0

    override fun init(trigger: Unit) {
        super.init(trigger)
        switcherDragHandler.setOnTouchPageChanged(touchPageMutable::postValue)
    }

    fun addLastDesktopPage() {
        viewModelScope.launch(back) {
            addDesktopPageUseCase(pageModelFactory.create(lastPagePosition + 1))
        }
    }

    fun removeLastDesktopPage() {
        viewModelScope.launch(back) {
            removeDesktopPageByPositionUseCase(lastPagePosition)
        }
    }

    fun handlePageDragTouch(touch: PointF?, page: Int, pageBounds: Rect) {
        switcherDragHandler.handleDragTouch(touch, page, pageBounds)
    }

    fun stopHandlePageDragTouch() {
        switcherDragHandler.stopHandleDragTouch()
    }
}
