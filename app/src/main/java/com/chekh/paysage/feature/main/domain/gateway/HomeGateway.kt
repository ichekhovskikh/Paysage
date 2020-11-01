package com.chekh.paysage.feature.main.domain.gateway

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.model.CategoryModel

interface HomeGateway {
    fun startObserveAppUpdates()
    fun stopObserveAppUpdates()
    fun startObserveWidgetUpdates()
    fun stopObserveWidgetUpdates()
    fun getDockApps(): LiveData<List<AppModel>>
    fun getDockAppSettings(): LiveData<AppSettingsModel>
    fun getAppCategories(): LiveData<List<CategoryModel>>
    fun getBoardApps(): LiveData<List<AppModel>>
    fun getBoardAppSettings(): LiveData<AppSettingsModel>
}
