package com.chekh.paysage.feature.main.domain.usecase.widget

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import javax.inject.Inject

class GetDesktopWidgetsByPageUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(pageId: Long): LiveData<List<DesktopWidgetModel>> =
        gateway.getDesktopWidgetsByPage(pageId)
}
