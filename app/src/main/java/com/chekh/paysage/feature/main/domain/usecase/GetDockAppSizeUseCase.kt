package com.chekh.paysage.feature.main.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class GetDockAppSizeUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<Int> =
        gateway.getDockAppSettings().map { it?.appSize }
}
