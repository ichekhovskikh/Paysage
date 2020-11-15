package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.AppModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDockAppsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): Flow<List<AppModel>> =
        gateway.getDockApps()
}
