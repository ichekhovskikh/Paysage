package com.chekh.paysage.feature.main.domain.usecase

import com.chekh.paysage.feature.main.domain.gateway.HomeGateway
import com.chekh.paysage.feature.main.domain.model.CategoryModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppCategoriesUseCase @Inject constructor(
    private val gateway: HomeGateway
) {

    operator fun invoke(): Flow<List<CategoryModel>> =
        gateway.getAppCategories()
}
