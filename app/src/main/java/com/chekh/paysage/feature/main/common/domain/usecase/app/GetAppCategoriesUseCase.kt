package com.chekh.paysage.feature.main.common.domain.usecase.app

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.common.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.common.domain.model.CategoryModel
import javax.inject.Inject

class GetAppCategoriesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<List<CategoryModel>> =
        gateway.getAppCategories()
}
