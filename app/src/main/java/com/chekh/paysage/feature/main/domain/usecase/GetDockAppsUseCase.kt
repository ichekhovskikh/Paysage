package com.chekh.paysage.feature.main.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.AppModel
import javax.inject.Inject

class GetDockAppsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<List<AppModel>> =
        gateway.getDockApps()
}