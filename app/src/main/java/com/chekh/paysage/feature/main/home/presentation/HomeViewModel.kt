package com.chekh.paysage.feature.main.home.presentation

import androidx.lifecycle.viewModelScope
import com.chekh.paysage.core.provider.back
import com.chekh.paysage.core.provider.ui
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.common.domain.usecase.app.PullBoardAppsUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.app.StartObserveAppUpdatesUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.app.StopObserveAppUpdatesUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.widget.PullDesktopWidgetsUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.widget.StartObserveWidgetEventsUseCase
import com.chekh.paysage.feature.main.common.domain.usecase.widget.StopObserveWidgetEventsUseCase
import com.chekh.paysage.feature.main.common.tools.onAppsChanged
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val startObserveAppUpdatesUseCase: StartObserveAppUpdatesUseCase,
    private val stopObserveAppUpdatesUseCase: StopObserveAppUpdatesUseCase,
    private val startObserveWidgetEventsUseCase: StartObserveWidgetEventsUseCase,
    private val stopObserveWidgetEventsUseCase: StopObserveWidgetEventsUseCase,
    private val pullBoardAppsUseCase: PullBoardAppsUseCase,
    private val pullDesktopWidgetsUseCase: PullDesktopWidgetsUseCase
) : BaseViewModel<Unit>() {

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
