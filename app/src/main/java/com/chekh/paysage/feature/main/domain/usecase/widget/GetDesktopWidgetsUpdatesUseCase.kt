package com.chekh.paysage.feature.main.domain.usecase.widget

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.distinctUntilChanged
import com.chekh.paysage.core.extension.ignoreValue
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import javax.inject.Inject

class GetDesktopWidgetsUpdatesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<Unit> =
        gateway.getDesktopWidgets()
            .distinctUntilChanged()
            .ignoreValue()
}
