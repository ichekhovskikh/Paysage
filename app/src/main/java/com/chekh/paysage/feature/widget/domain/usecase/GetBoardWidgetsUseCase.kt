package com.chekh.paysage.feature.widget.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import javax.inject.Inject

class GetBoardWidgetsUseCase @Inject constructor(
    private val gateway: WidgetGateway
) {

    operator fun invoke(): LiveData<List<WidgetModel>> =
        gateway.getBoardWidgets()
}
