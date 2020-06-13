package com.chekh.paysage.feature.home

import androidx.lifecycle.ViewModel
import com.chekh.paysage.feature.home.domain.usecase.StartObserveUpdatesUseCase
import com.chekh.paysage.feature.home.domain.usecase.StopObserveUpdatesUseCase
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