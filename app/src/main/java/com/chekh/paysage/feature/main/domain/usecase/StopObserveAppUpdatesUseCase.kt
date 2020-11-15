package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import javax.inject.Inject

class StopObserveAppUpdatesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(callback: AppsChangedCallback) {
        gateway.stopObserveAppUpdates(callback)
    }
}
