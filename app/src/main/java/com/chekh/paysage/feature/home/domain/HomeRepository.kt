package com.chekh.paysage.feature.home.domain

import androidx.lifecycle.LiveData
import com.chekh.paysage.feature.home.domain.gateway.AppsGroupByCategoriesGateway
import com.chekh.paysage.feature.home.domain.gateway.StartObserveUpdatesGateway
import com.chekh.paysage.feature.home.domain.gateway.StopObserveUpdatesGateway
import com.chekh.paysage.feature.home.domain.model.AppsGroupByCategoryModel

interface HomeRepository : AppsGroupByCategoriesGateway, StartObserveUpdatesGateway,
    StopObserveUpdatesGateway {

    override fun getAppsGroupByCategories(): LiveData<List<AppsGroupByCategoryModel>>

    override fun startObserveUpdates()

    override fun stopObserveUpdates()

}