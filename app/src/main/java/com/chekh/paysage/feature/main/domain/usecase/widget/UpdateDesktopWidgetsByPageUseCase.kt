package com.chekh.paysage.feature.main.domain.usecase.widget

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import javax.inject.Inject

class UpdateDesktopWidgetsByPageUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(pageId: Long, widgets: List<DesktopWidgetModel>?) =
        gateway.updateDesktopWidgetsByPage(pageId, widgets)
}
