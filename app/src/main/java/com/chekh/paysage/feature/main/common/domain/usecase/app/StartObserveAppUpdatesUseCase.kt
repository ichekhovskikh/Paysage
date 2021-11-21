package com.chekh.paysage.feature.main.common.domain.usecase.app

import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.common.tools.AppsChangedCallback
import javax.inject.Inject

class StartObserveAppUpdatesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(callback: AppsChangedCallback) {
        gateway.startObserveAppUpdates(callback)
    }
}
