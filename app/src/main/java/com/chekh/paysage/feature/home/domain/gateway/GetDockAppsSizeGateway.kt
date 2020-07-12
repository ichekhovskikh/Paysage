package com.chekh.paysage.feature.home.domain.gateway

import androidx.lifecycle.LiveData

interface GetDockAppsSizeGateway {
    fun getDockAppsSize(): LiveData<Int>
}