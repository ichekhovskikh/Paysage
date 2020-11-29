package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import javax.inject.Inject

class AddDesktopWidgetUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(widget: DesktopWidgetModel) =
        gateway.addDesktopWidget(widget)
}
