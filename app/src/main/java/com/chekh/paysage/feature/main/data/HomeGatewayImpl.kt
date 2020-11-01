package com.chekh.paysage.feature.main.data

import com.chekh.paysage.core.provider.SettingsProvider
import com.chekh.paysage.feature.main.data.service.AppService
import com.chekh.paysage.feature.main.data.service.CategoryService
import com.chekh.paysage.feature.main.data.service.DesktopWidgetService
import com.chekh.paysage.feature.main.data.service.DockAppService
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class HomeGatewayImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val settingsProvider: SettingsProvider,
    private val appService: AppService,
    private val dockAppService: DockAppService,
    private val desktopWidgetService: DesktopWidgetService
) : HomeGateway {

    override fun getDockApps() = dockAppService.dockAppsLiveData

    override fun getDockAppSettings() = settingsProvider.dockAppSettingsLiveData

    override fun getAppCategories() = categoryService.categoriesLiveData

    override fun getBoardApps() = appService.installedAppsLiveData

    override fun getBoardAppSettings() = settingsProvider.boardAppSettingsLiveData

    override fun startObserveAppUpdates() {
        appService.startObserveUpdates()
    }

    override fun stopObserveAppUpdates() {
        appService.stopObserveUpdates()
    }

    override fun startObserveWidgetUpdates() {
        desktopWidgetService.startObserveUpdates()
    }

    override fun stopObserveWidgetUpdates() {
        desktopWidgetService.stopObserveUpdates()
    }
}
