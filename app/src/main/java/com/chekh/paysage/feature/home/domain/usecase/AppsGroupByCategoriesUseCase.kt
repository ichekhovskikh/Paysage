package com.chekh.paysage.feature.home.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.domain.gateway.AppsGroupByCategoriesGateway
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel
import javax.inject.Inject

class AppsGroupByCategoriesUseCase @Inject constructor(
    private val gateway: AppsGroupByCategoriesGateway
) {

    operator fun invoke(): LiveData<List<AppsGroupByCategoryModel>> =
        gateway.getAppsGroupByCategories()
}