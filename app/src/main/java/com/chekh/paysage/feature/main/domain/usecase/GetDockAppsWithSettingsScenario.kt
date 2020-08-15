package com.chekh.paysage.feature.main.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.extension.zip
import com.chekh.paysage.feature.main.domain.model.DockAppsModel
import javax.inject.Inject

class GetDockAppsWithSettingsScenario @Inject constructor(
    private val getDockAppsUseCase: GetDockAppsUseCase,
    private val getDockAppSettingsUseCase: GetDockAppSettingsUseCase
) {

    operator fun invoke(): LiveData<DockAppsModel> = zip(
        getDockAppsUseCase(),
        getDockAppSettingsUseCase()
    ) { dockApps, dockAppSettings ->
        DockAppsModel(dockApps, dockAppSettings)
    }
}