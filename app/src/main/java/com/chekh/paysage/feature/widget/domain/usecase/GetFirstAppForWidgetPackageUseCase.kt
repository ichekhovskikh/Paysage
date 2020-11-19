package com.chekh.paysage.feature.widget.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import com.chekh.paysage.feature.widget.domain.model.AppForWidgetModel
import javax.inject.Inject

class GetFirstAppForWidgetPackageUseCase @Inject constructor(
    private val gateway: WidgetGateway
) {

    operator fun invoke(packageName: String): LiveData<AppForWidgetModel> =
        gateway.getFirstAppForWidgetPackage(packageName)
}
