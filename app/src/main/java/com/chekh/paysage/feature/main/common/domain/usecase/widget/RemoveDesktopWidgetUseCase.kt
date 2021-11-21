package com.chekh.paysage.feature.main.common.domain.usecase.widget

import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import javax.inject.Inject

class RemoveDesktopWidgetUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(widgetId: String) =
        gateway.removeDesktopWidget(widgetId)
}
