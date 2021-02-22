package com.chekh.paysage.feature.main.domain.usecase.page

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class RemoveDesktopPageUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(pageId: Long) =
        gateway.removeDesktopPage(pageId)
}
