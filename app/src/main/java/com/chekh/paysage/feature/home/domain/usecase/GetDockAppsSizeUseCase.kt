package com.chekh.paysage.feature.home.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.domain.gateway.GetDockAppsSizeGateway
import javax.inject.Inject

class GetDockAppsSizeUseCase @Inject constructor(
    private val gateway: GetDockAppsSizeGateway
) {

    operator fun invoke(): LiveData<Int> =
        gateway.getDockAppsSize()
}