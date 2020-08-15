package com.chekh.paysage.feature.main.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.AppSettingsModel
import javax.inject.Inject

class GetMenuAppSettingsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<AppSettingsModel> =
        gateway.getMenuAppSettings()
}