package com.chekh.paysage.feature.main.presentation.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.provider.back
import com.chekh.paysage.core.provider.ui
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.usecase.app.PullBoardAppsUseCase
import com.chekh.paysage.feature.main.domain.usecase.app.StartObserveAppUpdatesUseCase
import com.chekh.paysage.feature.main.domain.usecase.app.StopObserveAppUpdatesUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.PullDesktopWidgetsUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.StartObserveWidgetEventsUseCase
import com.chekh.paysage.feature.main.domain.usecase.widget.StopObserveWidgetEventsUseCase
import com.chekh.paysage.feature.main.tools.onAppsChanged
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val startObserveAppUpdatesUseCase: StartObserveAppUpdatesUseCase,
    private val stopObserveAppUpdatesUseCase: StopObserveAppUpdatesUseCase,
    private val startObserveWidgetEventsUseCase: StartObserveWidgetEventsUseCase,
    private val stopObserveWidgetEventsUseCase: StopObserveWidgetEventsUseCase,
    private val pullBoardAppsUseCase: PullBoardAppsUseCase,
    private val pullDesktopWidgetsUseCase: PullDesktopWidgetsUseCase
) : BaseViewModel<Unit>() {

    val isEnabledOverlayHomeButtonsLiveData = MutableLiveData<Boolean>()

    private val appsChangedCallback = onAppsChanged { packageName, _ -> pullAll(packageName) }

    override fun init(trigger: Unit) {
        startObserveUpdates()
        pullAll()
        super.init(trigger)
    }

    private fun pullAll(packageName: String? = null) {
        viewModelScope.launch(back) {
            pullBoardAppsUseCase(packageName)
            pullDesktopWidgetsUseCase()
        }
    }

    private fun startObserveUpdates() {
        viewModelScope.launch(ui) {
            startObserveAppUpdatesUseCase(appsChangedCallback)
            startObserveWidgetEventsUseCase()
        }
    }

    private fun stopObserveUpdates() {
        viewModelScope.launch(ui) {
            stopObserveAppUpdatesUseCase(appsChangedCallback)
            stopObserveWidgetEventsUseCase()
        }
    }

    override fun onCleared() {
        stopObserveUpdates()
        super.onCleared()
    }
}
