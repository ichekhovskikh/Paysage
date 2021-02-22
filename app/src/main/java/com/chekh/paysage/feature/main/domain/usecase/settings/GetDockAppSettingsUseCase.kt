package com.chekh.paysage.feature.main.domain.usecase.settings

import androidx.lifecycle.LiveData
import com.chekh.paysage.common.domain.model.AppSettingsModel
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class GetDockAppSettingsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<AppSettingsModel> =
        gateway.getDockAppSettings()
}
