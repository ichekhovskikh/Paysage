package com.chekh.paysage.feature.main.presentation.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.provider.DispatcherProvider
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.usecase.*
import com.chekh.paysage.feature.main.tools.onAppsChanged
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val startObserveAppUpdatesUseCase: StartObserveAppUpdatesUseCase,
    private val stopObserveAppUpdatesUseCase: StopObserveAppUpdatesUseCase,
    private val startObserveWidgetEventsUseCase: StartObserveWidgetEventsUseCase,
    private val stopObserveWidgetEventsUseCase: StopObserveWidgetEventsUseCase,
    private val pullBoardAppsUseCase: PullBoardAppsUseCase,
    private val pullDesktopWidgetsUseCase: PullDesktopWidgetsUseCase
) : BaseViewModel<Unit>() {

    val isEnabledOverlayHomeButtonsLiveData = MutableLiveData<Boolean>()

    private val appsChangedCallback = onAppsChanged { packageName, _ ->
        viewModelScope.launch(dispatcherProvider.io) {
            pullBoardAppsUseCase(packageName)
            pullDesktopWidgetsUseCase(packageName)
        }
    }

    override fun init(trigger: Unit) {
        startObserveUpdates()
        super.init(trigger)
    }

    private fun startObserveUpdates() {
        viewModelScope.launch(dispatcherProvider.main) {
            startObserveAppUpdatesUseCase(appsChangedCallback)
            startObserveWidgetEventsUseCase()
        }
    }

    private fun stopObserveUpdates() {
        viewModelScope.launch(dispatcherProvider.main) {
            stopObserveAppUpdatesUseCase(appsChangedCallback)
            stopObserveWidgetEventsUseCase()
        }
    }

    override fun onCleared() {
        stopObserveUpdates()
        super.onCleared()
    }
}
