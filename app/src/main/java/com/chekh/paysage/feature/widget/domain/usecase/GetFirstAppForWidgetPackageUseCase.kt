package com.chekh.paysage.feature.widget.domain.usecase

import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import com.chekh.paysage.feature.widget.domain.model.WidgetAppModel
import javax.inject.Inject

class GetFirstAppForWidgetPackageUseCase @Inject constructor(
    private val gateway: WidgetGateway
) {

    suspend operator fun invoke(packageName: String): WidgetAppModel =
        gateway.getFirstAppForWidgetPackage(packageName)
}
