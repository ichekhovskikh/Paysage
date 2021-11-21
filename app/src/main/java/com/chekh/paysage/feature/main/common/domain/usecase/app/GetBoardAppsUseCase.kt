package com.chekh.paysage.feature.main.common.domain.usecase.app

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.common.domain.model.AppModel
import javax.inject.Inject

class GetBoardAppsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<List<AppModel>> =
        gateway.getBoardApps()
}
