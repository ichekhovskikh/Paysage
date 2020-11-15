package com.chekh.paysage.feature.main.data

import com.chekh.paysage.common.data.service.SettingsService
import com.chekh.paysage.feature.main.data.service.AppService
import com.chekh.paysage.feature.main.data.service.CategoryService
import com.chekh.paysage.feature.main.data.service.DesktopWidgetService
import com.chekh.paysage.feature.main.data.service.DockAppService
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.tools.AppsChangedCallback
import javax.inject.Inject

class HomeGatewayImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val settingsService: SettingsService,
    private val appService: AppService,
    private val dockAppService: DockAppService,
    private val desktopWidgetService: DesktopWidgetService
) : HomeGateway {

    override fun getDockApps() = dockAppService.dockApps

    override fun getDockAppSettings() = settingsService.dockAppSettings

    override fun getAppCategories() = categoryService.categories

    override fun getBoardApps() = appService.installedApps

    override fun getBoardAppSettings() = settingsService.boardAppSettings

    override suspend fun startObserveAppUpdates(callback: AppsChangedCallback) {
        appService.startObserveAppUpdates(callback)
    }

    override suspend fun stopObserveAppUpdates(callback: AppsChangedCallback) {
        appService.stopObserveAppUpdates(callback)
    }

    override suspend fun startObserveWidgetEvents() {
        desktopWidgetService.startObserveWidgetEvents()
    }

    override suspend fun stopObserveWidgetEvents() {
        desktopWidgetService.stopObserveWidgetEvents()
    }

    override suspend fun pullBoardApps(packageName: String) {
        appService.pullApps(packageName)
    }

    override suspend fun pullDesktopWidgets(packageName: String) {
        desktopWidgetService.pullWidgets(packageName)
    }
}
