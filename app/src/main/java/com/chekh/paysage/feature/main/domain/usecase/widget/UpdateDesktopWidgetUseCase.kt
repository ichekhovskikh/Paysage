package com.chekh.paysage.feature.main.domain.usecase.widget

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import javax.inject.Inject

class UpdateDesktopWidgetUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(widget: DesktopWidgetModel) =
        gateway.updateDesktopWidget(widget)
}
