package com.chekh.paysage.feature.main.domain.gateway

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.model.CategoryModel

interface HomeGateway {
    fun startObserveUpdates()
    fun stopObserveUpdates()
    fun getDockApps(): LiveData<List<AppModel>>
    fun getDockAppSettings(): LiveData<AppSettingsModel>
    fun getAppCategories(): LiveData<List<CategoryModel>>
    fun getMenuApps(): LiveData<List<AppModel>>
    fun getMenuAppSettings(): LiveData<AppSettingsModel>
}