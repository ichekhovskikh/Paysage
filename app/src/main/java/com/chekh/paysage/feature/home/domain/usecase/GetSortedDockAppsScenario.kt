package com.chekh.paysage.feature.home.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.extension.sortedBy
import com.chekh.paysage.extension.zip
import com.chekh.paysage.feature.home.domain.model.AppListModel
import javax.inject.Inject

class GetSortedDockAppsScenario @Inject constructor(
    private val getDockAppsUseCase: GetDockAppsUseCase,
    private val getDockAppsPositionsUseCase: GetDockAppsPositionsUseCase,
    private val getDockAppsSizeUseCase: GetDockAppsSizeUseCase
) {

    operator fun invoke(): LiveData<AppListModel> =
        zip(
            getDockAppsPositionsUseCase(),
            getDockAppsUseCase(),
            getDockAppsSizeUseCase()
        ) { dockPositions, dockApps, size ->
            val sortedApps = dockApps.sortedBy { app ->
                dockPositions.find { (id, _) -> id == app.id }?.second
            }
            AppListModel(sortedApps, size, 1)
        }

}