package com.chekh.paysage.feature.main.common.domain.usecase.app

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.common.domain.model.AppModel
import javax.inject.Inject

class GetDockAppsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(count: Int): LiveData<List<AppModel>> =
        gateway.getDockApps()
            .map { it?.take(count) }
}
