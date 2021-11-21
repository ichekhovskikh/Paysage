package com.chekh.paysage.feature.main.common.domain.usecase.widget

import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetModel
import javax.inject.Inject

class UpdateDesktopWidgetUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(widget: DesktopWidgetModel) =
        gateway.updateDesktopWidget(widget)
}
