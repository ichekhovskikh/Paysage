package com.chekh.paysage.feature.widget.domain.usecase

import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject

class PullBoardWidgetsUseCase @Inject constructor(
    private val gateway: WidgetGateway
) {

    suspend operator fun invoke() = gateway.pullBoardWidgets()
}
