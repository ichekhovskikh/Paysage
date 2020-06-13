package com.chekh.paysage.feature.home.domain.usecase

import com.chekh.paysage.feature.home.domain.gateway.StartObserveUpdatesGateway
import javax.inject.Inject

class StartObserveUpdatesUseCase @Inject constructor(
    private val gateway: StartObserveUpdatesGateway
) {

    operator fun invoke() {
        gateway.startObserveUpdates()
    }
}