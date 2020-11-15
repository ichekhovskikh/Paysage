package com.chekh.paysage.feature.widget.domain.usecase

import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject

class StopObserveBoardWidgetUpdatesUseCase @Inject constructor(
    private val gateway: WidgetGateway
) {

    suspend operator fun invoke(callback: AppsChangedCallback) =
        gateway.stopObserveBoardWidgetUpdates(callback)
}
