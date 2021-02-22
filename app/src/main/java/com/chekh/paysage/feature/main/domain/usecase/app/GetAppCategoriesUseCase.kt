package com.chekh.paysage.feature.main.domain.usecase.app

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.CategoryModel
import javax.inject.Inject

class GetAppCategoriesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): LiveData<List<CategoryModel>> =
        gateway.getAppCategories()
}
