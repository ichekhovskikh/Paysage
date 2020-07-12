package com.chekh.paysage.feature.home.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.domain.gateway.GetDockAppsGateway
import com.chekh.paysage.feature.home.domain.model.AppModel
import javax.inject.Inject

class GetDockAppsUseCase @Inject constructor(
    private val gateway: GetDockAppsGateway
) {

    operator fun invoke(): LiveData<List<AppModel>> =
        gateway.getDockApps()
}