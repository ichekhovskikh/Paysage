package com.chekh.paysage.feature.main.presentation.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.usecase.StartObserveAppUpdatesUseCase
import com.chekh.paysage.feature.main.domain.usecase.StartObserveWidgetUpdatesUseCase
import com.chekh.paysage.feature.main.domain.usecase.StopObserveAppUpdatesUseCase
import com.chekh.paysage.feature.main.domain.usecase.StopObserveWidgetUpdatesUseCase

class HomeViewModel @ViewModelInject constructor(
    private val startObserveAppUpdatesUseCase: StartObserveAppUpdatesUseCase,
    private val stopObserveAppUpdatesUseCase: StopObserveAppUpdatesUseCase,
    private val startObserveWidgetUpdatesUseCase: StartObserveWidgetUpdatesUseCase,
    private val stopObserveWidgetUpdatesUseCase: StopObserveWidgetUpdatesUseCase
) : BaseViewModel<Unit>() {

    val isEnabledHomeButtonsLiveData = MutableLiveData<Boolean>()

    fun startObserveUpdates() {
        startObserveAppUpdatesUseCase()
        startObserveWidgetUpdatesUseCase()
    }

    fun stopObserveUpdates() {
        stopObserveAppUpdatesUseCase()
        stopObserveWidgetUpdatesUseCase()
    }
}
