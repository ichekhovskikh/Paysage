package com.chekh.paysage.feature.widget.data

import com.chekh.paysage.common.data.service.SettingsService
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import com.chekh.paysage.feature.widget.data.service.WidgetService
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject

class WidgetGatewayImpl @Inject constructor(
    private val widgetService: WidgetService,
    private val settingsService: SettingsService
) : WidgetGateway {

    override fun getDesktopGridSpan() = settingsService.desktopGridSpan

    override fun getBoardWidgets() = widgetService.installedWidgets

    override suspend fun getFirstAppForWidgetPackage(packageName: String) =
        widgetService.getFirstAppForWidgetPackage(packageName)

    override suspend fun pullBoardWidgets() {
        widgetService.pullWidgets()
    }

    override suspend fun startObserveBoardWidgetUpdates(callback: AppsChangedCallback) {
        widgetService.startObserveWidgetUpdates(callback)
    }

    override suspend fun stopObserveBoardWidgetUpdates(callback: AppsChangedCallback) {
        widgetService.stopObserveWidgetUpdates(callback)
    }
}
