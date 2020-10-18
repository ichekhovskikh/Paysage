package com.chekh.paysage.feature.main.presentation.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.chekh.paysage.feature.main.domain.usecase.StartObserveUpdatesUseCase
import com.chekh.paysage.feature.main.domain.usecase.StopObserveUpdatesUseCase

class HomeViewModel @ViewModelInject constructor(
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
