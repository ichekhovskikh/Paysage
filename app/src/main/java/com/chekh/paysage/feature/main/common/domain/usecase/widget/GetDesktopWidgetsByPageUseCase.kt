package com.chekh.paysage.feature.main.common.domain.usecase.widget

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetModel
import javax.inject.Inject

class GetDesktopWidgetsByPageUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(pageId: Long): LiveData<List<DesktopWidgetModel>> =
        gateway.getDesktopWidgetsByPage(pageId)
}
