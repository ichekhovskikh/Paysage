package com.chekh.paysage.feature.home.apps.tool

import com.chekh.paysage.feature.home.apps.model.AppsDataViewState
import com.chekh.paysage.feature.home.apps.view.AppsDataView

fun AppsDataView.save(state: AppsDataViewState?) {
    state?.isExpanded = isExpanded
    state?.scrollX = appsScrollX
}

fun AppsDataView.restore(state: AppsDataViewState?) {
    isExpanded = state?.isExpanded ?: false
    appsScrollX = state?.scrollX ?: 0
}