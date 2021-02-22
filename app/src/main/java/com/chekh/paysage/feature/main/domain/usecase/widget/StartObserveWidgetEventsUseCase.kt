package com.chekh.paysage.feature.main.domain.usecase.widget

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class StartObserveWidgetEventsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke() {
        gateway.startObserveWidgetEvents()
    }
}