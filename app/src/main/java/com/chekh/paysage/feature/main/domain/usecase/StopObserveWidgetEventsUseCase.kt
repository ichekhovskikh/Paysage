package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class StopObserveWidgetEventsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke() {
        gateway.stopObserveWidgetEvents()
    }
}
