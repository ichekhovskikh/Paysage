package com.chekh.paysage.feature.home.domain.usecase

import com.chekh.paysage.feature.home.domain.gateway.StopObserveUpdatesGateway
import javax.inject.Inject

class StopObserveUpdatesUseCase @Inject constructor(
    private val gateway: StopObserveUpdatesGateway
) {

    operator fun invoke() {
        gateway.stopObserveUpdates()
    }
}