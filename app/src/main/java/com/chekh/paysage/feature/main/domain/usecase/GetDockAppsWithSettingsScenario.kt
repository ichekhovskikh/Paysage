package com.chekh.paysage.feature.main.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.zip
import com.chekh.paysage.feature.main.domain.mapper.AppsModelMapper
import com.chekh.paysage.feature.main.domain.model.AppsModel
import javax.inject.Inject

class GetDockAppsWithSettingsScenario @Inject constructor(
    private val getDockAppsUseCase: GetDockAppsUseCase,
    private val getDockAppSettingsUseCase: GetDockAppSettingsUseCase,
    private val appsModelMapper: AppsModelMapper
) {

    operator fun invoke(): LiveData<AppsModel> = zip(
        getDockAppsUseCase(),
        getDockAppSettingsUseCase()
    ) { dockApps, dockAppSettings ->
        appsModelMapper.map(dockApps, dockAppSettings)
    }
}
