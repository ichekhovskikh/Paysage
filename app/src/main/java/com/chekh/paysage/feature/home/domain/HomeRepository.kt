package com.chekh.paysage.feature.home.domain

import com.chekh.paysage.feature.home.domain.gateway.*

interface HomeRepository :
    AppsGroupByCategoriesGateway,
    GetDockAppsGateway,
    GetDockAppsPositionsGateway,
    StartObserveUpdatesGateway,
    StopObserveUpdatesGateway,
    GetDockAppsSizeGateway