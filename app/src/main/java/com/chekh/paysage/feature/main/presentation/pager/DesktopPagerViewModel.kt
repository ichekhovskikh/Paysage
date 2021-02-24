package com.chekh.paysage.feature.main.presentation.pager

import androidx.hilt.lifecycle.ViewModelInject
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
import kotlinx.coroutines.launch

class DesktopPagerViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val getDesktopPagesUseCase: GetDesktopPagesUseCase,
    private val addDesktopPageUseCase: AddDesktopPageUseCase,
    private val removeEmptyDesktopPagesUseCase: RemoveEmptyDesktopPagesUseCase,
    private val getDesktopWidgetsUpdatesUseCase: GetDesktopWidgetsUpdatesUseCase,
    private val pageModelFactory: DesktopPageModelFactory
) : BaseViewModel<Unit>() {

    val desktopWidgetsUpdatesLiveData = trigger
        .switchMap { getDesktopWidgetsUpdatesUseCase() }
        .ignoreFirst()

    val pagesLiveData = trigger
        .switchMap { getDesktopPagesUseCase() }
        .distinctUntilChanged()

    fun addDesktopPage(position: Int) {
        viewModelScope.launch(dispatcherProvider.back) {
            addDesktopPageUseCase(pageModelFactory.create(position))
        }
    }

    fun removeEmptyDesktopPages() {
        viewModelScope.launch(dispatcherProvider.back) {
            removeEmptyDesktopPagesUseCase()
        }
    }
}
