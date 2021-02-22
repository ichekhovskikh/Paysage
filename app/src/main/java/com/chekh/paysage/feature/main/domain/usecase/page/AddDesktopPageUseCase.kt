package com.chekh.paysage.feature.main.domain.usecase.page

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import javax.inject.Inject

class AddDesktopPageUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    suspend operator fun invoke(page: DesktopPageModel) =
        gateway.addDesktopPage(page)
}
