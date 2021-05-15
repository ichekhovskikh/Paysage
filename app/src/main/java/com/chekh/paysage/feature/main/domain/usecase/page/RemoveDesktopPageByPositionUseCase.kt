package com.chekh.paysage.feature.main.domain.usecase.page

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class RemoveDesktopPageByPositionUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(position: Int) =
        gateway.removeDesktopPageByPosition(position)
}
