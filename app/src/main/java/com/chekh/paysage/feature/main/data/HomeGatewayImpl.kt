package com.chekh.paysage.feature.main.data

import com.chekh.paysage.common.data.datasource.SettingsDataSource
import com.chekh.paysage.feature.main.data.datasource.*
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import javax.inject.Inject

class HomeGatewayImpl @Inject constructor(
    private val categoryDataSource: CategoryDataSource,
    private val settingsDataSource: SettingsDataSource,
    private val appDataSource: AppDataSource,
    private val dockAppDataSource: DockAppDataSource,
    private val desktopWidgetDataSource: DesktopWidgetDataSource,
    private val desktopPageDataSource: DesktopPageDataSource
) : HomeGateway {

    override fun getDockApps() = dockAppDataSource.dockApps

    override fun getDockAppSettings() = settingsDataSource.dockAppSettings

    override fun getAppCategories() = categoryDataSource.categories

    override fun getBoardApps() = appDataSource.installedApps

    override fun getBoardAppSettings() = settingsDataSource.boardAppSettings

    override fun getDesktopGridSize() = settingsDataSource.desktopGridSize

    override fun getDesktopPages() = desktopPageDataSource.desktopPages

    override fun getDesktopWidgets() = desktopWidgetDataSource.desktopWidgets

    override fun getDesktopWidgetsByPage(pageId: Long) = desktopWidgetDataSource.getDesktopWidgetsByPage(pageId)

    override suspend fun startObserveAppUpdates(callback: AppsChangedCallback) {
        appDataSource.startObserveAppUpdates(callback)
    }

    override suspend fun stopObserveAppUpdates(callback: AppsChangedCallback) {
        appDataSource.stopObserveAppUpdates(callback)
    }

    override suspend fun startObserveWidgetEvents() {
        desktopWidgetDataSource.startObserveWidgetEvents()
    }

    override suspend fun stopObserveWidgetEvents() {
        desktopWidgetDataSource.stopObserveWidgetEvents()
    }

    override suspend fun pullBoardApps(packageName: String?) {
        appDataSource.pullApps(packageName)
    }

    override suspend fun pullDesktopWidgets() {
        desktopWidgetDataSource.pullWidgets()
    }

    override suspend fun updateDesktopWidget(widget: DesktopWidgetModel) {
        desktopWidgetDataSource.updateDesktopWidget(widget)
    }

    override suspend fun updateDesktopWidgetsByPage(
        pageId: Long,
        widgets: List<DesktopWidgetModel>?
    ) {
        desktopWidgetDataSource.updateDesktopWidgetsByPage(pageId, widgets)
    }

    override suspend fun removeDesktopWidget(widgetId: String) {
        desktopWidgetDataSource.removeDesktopWidget(widgetId)
    }

    override suspend fun addDesktopPage(page: DesktopPageModel) {
        desktopPageDataSource.addDesktopPage(page)
    }

    override suspend fun removeDesktopPageByPosition(position: Int) {
        desktopPageDataSource.removeDesktopPageByPosition(position)
    }
}
