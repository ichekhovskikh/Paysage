package com.chekh.paysage.feature.main.screen.home

import androidx.lifecycle.ViewModel
import com.chekh.paysage.feature.main.domain.usecase.StartObserveUpdatesUseCase
import com.chekh.paysage.feature.main.domain.usecase.StopObserveUpdatesUseCase
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val startObserveUpdatesUseCase: StartObserveUpdatesUseCase,
    private val stopObserveUpdatesUseCase: StopObserveUpdatesUseCase
) : ViewModel() {

    fun startObserveUpdates() {
        startObserveUpdatesUseCase()
    }

    fun stopObserveUpdates() {
        stopObserveUpdatesUseCase()
    }
}