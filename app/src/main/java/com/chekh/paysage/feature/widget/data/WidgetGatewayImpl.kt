package com.chekh.paysage.feature.widget.data

import com.chekh.paysage.common.data.service.SettingsService
import com.chekh.paysage.feature.widget.data.service.WidgetService
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject

class WidgetGatewayImpl @Inject constructor(
    private val settingsService: SettingsService,
    private val widgetService: WidgetService
) : WidgetGateway {

    override fun getBoardWidgets() = widgetService.installedWidgets

    override fun getFirstAppForWidgetPackage(packageName: String) =
        widgetService.getFirstAppForWidgetPackage(packageName)

    override fun getDesktopGridSpan() = settingsService.desktopGridSpan

}
