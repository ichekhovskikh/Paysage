package com.chekh.paysage.feature.home.domain.gateway

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel

interface AppsGroupByCategoriesGateway {
    fun getAppsGroupByCategories(): LiveData<List<AppsGroupByCategoryModel>>
}