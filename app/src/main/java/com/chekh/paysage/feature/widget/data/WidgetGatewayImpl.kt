package com.chekh.paysage.feature.widget.data

import com.chekh.paysage.feature.widget.data.service.WidgetService
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject

class WidgetGatewayImpl @Inject constructor(
    private val widgetService: WidgetService
) : WidgetGateway {

    override fun getBoardWidgets() = widgetService.installedWidgetsLiveData

    override fun getFirstWidgetAppByPackage(packageName: String) =
        widgetService.getFirstWidgetAppByPackage(packageName)
}
