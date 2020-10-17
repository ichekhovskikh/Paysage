package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class StopObserveUpdatesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke() {
        gateway.stopObserveUpdates()
    }
}
