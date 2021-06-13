package com.chekh.paysage.feature.widget.data

import com.chekh.paysage.common.data.datasource.SettingsDataSource
import com.chekh.paysage.feature.widget.data.datasource.WidgetDataSource
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject

class WidgetGatewayImpl @Inject constructor(
    private val settingsDataSource: SettingsDataSource,
    private val widgetDataSource: WidgetDataSource
) : WidgetGateway {

    override fun getBoardWidgets() = widgetDataSource.installedWidgets

    override fun getFirstAppForWidgetPackage(packageName: String) =
        widgetDataSource.getFirstAppForWidgetPackage(packageName)

    override fun getDesktopGridSize() = settingsDataSource.desktopGridSize

}
