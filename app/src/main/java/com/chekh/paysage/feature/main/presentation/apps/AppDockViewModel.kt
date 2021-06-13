package com.chekh.paysage.feature.main.presentation.apps

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.core.extension.distinctUntilChanged
import com.chekh.paysage.core.extension.switchMap
import com.chekh.paysage.core.ui.viewmodel.BaseViewModel
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.usecase.app.GetDockAppsUseCase
import com.chekh.paysage.feature.main.domain.usecase.settings.GetDockAppSettingsUseCase

class AppDockViewModel @ViewModelInject constructor(
    private val getDockAppsUseCase: GetDockAppsUseCase,
    private val getDockAppSettingsUseCase: GetDockAppSettingsUseCase
) : BaseViewModel<Unit>() {

    val isAppDockVisible = MutableLiveData<Boolean>()

    val appDockSize = MutableLiveData<Int>()

    val dockAppSettings: LiveData<AppSettingsModel> = trigger
        .switchMap { getDockAppSettingsUseCase() }
        .distinctUntilChanged()

    val dockApps: LiveData<List<AppModel>> = dockAppSettings
        .switchMap { getDockAppsUseCase(it.appColumnCount) }
        .distinctUntilChanged()
}
