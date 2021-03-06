package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class StopObserveWidgetUpdatesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke() {
        gateway.stopObserveWidgetUpdates()
    }
}
