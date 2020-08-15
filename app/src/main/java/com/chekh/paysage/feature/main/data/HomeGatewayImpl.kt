package com.chekh.paysage.feature.main.data

import com.chekh.paysage.data.service.SettingsService
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.data.service.AppService
import com.chekh.paysage.feature.main.data.service.CategoryService
import com.chekh.paysage.feature.main.data.service.DockAppService
import javax.inject.Inject

class HomeGatewayImpl @Inject constructor(
    private val categoryService: CategoryService,
    private val settingsService: SettingsService,
    private val appService: AppService,
    private val dockAppService: DockAppService
) : HomeGateway {

    override fun getDockApps() = dockAppService.dockAppsLiveData

    override fun getDockAppSettings() = settingsService.dockAppSettingsLiveData

    override fun getAppCategories() = categoryService.categoriesLiveData

    override fun getMenuApps() = appService.appsLiveData

    override fun getMenuAppSettings() = settingsService.menuAppSettingsLiveData

    override fun startObserveUpdates() {
        appService.startObserveUpdates()
    }

    override fun stopObserveUpdates() {
        appService.stopObserveUpdates()
    }
}