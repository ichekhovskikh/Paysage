package com.chekh.paysage.feature.home.domain.gateway

import androidx.lifecycle.LiveData

interface GetDockAppsPositionsGateway {
    fun getDockAppsPositions(): LiveData<List<Pair<String, Int>>>
}