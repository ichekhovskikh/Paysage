package com.chekh.paysage.feature.main.domain.usecase.settings

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class GetDesktopGridSizeUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke() = gateway.getDesktopGridSize()
}
