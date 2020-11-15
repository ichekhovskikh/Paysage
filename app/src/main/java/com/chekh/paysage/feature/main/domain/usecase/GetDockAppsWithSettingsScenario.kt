package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.mapper.AppsModelMapper
import com.chekh.paysage.feature.main.domain.model.AppsModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject

class GetDockAppsWithSettingsScenario @Inject constructor(
    private val getDockAppsUseCase: GetDockAppsUseCase,
    private val getDockAppSettingsUseCase: GetDockAppSettingsUseCase,
    private val appsModelMapper: AppsModelMapper
) {

    operator fun invoke(): Flow<AppsModel> =
        getDockAppsUseCase().zip(getDockAppSettingsUseCase()) { dockApps, dockAppSettings ->
            appsModelMapper.map(dockApps, dockAppSettings)
        }
}
