package com.chekh.paysage.feature.home.screen.apps

import androidx.lifecycle.LiveData
import com.chekh.paysage.extension.*
import com.chekh.paysage.feature.home.domain.model.AppListModel
import com.chekh.paysage.feature.home.domain.usecase.GetSortedDockAppsScenario
import com.chekh.paysage.ui.viewmodel.BaseViewModel
import javax.inject.Inject

class DockAppsViewModel @Inject constructor(
    private val getSortedDockAppsScenario: GetSortedDockAppsScenario
) : BaseViewModel<Unit>() {

    val dockAppsLiveData: LiveData<AppListModel> = trigger
        .switchMap { getSortedDockAppsScenario() }
}