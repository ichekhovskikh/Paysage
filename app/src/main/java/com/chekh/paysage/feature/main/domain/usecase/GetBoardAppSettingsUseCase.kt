package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBoardAppSettingsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): Flow<AppSettingsModel> =
        gateway.getBoardAppSettings()
}
