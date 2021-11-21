package com.chekh.paysage.feature.main.common.domain.usecase.settings

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import javax.inject.Inject

class GetDockAppSettingsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<AppSettingsModel> =
        gateway.getDockAppSettings()
}
