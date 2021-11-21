package com.chekh.paysage.feature.main.common.domain.usecase.app

import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import javax.inject.Inject

class PullBoardAppsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(packageName: String?) =
        gateway.pullBoardApps(packageName)
}
