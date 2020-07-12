package com.chekh.paysage.feature.home.domain.gateway

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.domain.model.AppModel

interface GetDockAppsGateway {
    fun getDockApps(): LiveData<List<AppModel>>
}