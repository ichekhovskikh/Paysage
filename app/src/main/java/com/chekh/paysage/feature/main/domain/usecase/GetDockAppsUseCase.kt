package com.chekh.paysage.feature.main.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.AppModel
import javax.inject.Inject

class GetDockAppsUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(count: Int): LiveData<List<AppModel>> =
        gateway.getDockApps()
            .map { it?.take(count) }
}
