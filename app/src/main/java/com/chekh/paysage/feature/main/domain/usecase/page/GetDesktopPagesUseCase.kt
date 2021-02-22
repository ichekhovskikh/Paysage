package com.chekh.paysage.feature.main.domain.usecase.page

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.DesktopPageModel
import javax.inject.Inject

class GetDesktopPagesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<List<DesktopPageModel>> =
        gateway.getDesktopPages()
}
