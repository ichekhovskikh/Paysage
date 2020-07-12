package com.chekh.paysage.feature.home.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.domain.gateway.GetDockAppsPositionsGateway
import javax.inject.Inject

class GetDockAppsPositionsUseCase @Inject constructor(
    private val gateway: GetDockAppsPositionsGateway
) {

    operator fun invoke(): LiveData<List<Pair<String, Int>>> =
        gateway.getDockAppsPositions()
}