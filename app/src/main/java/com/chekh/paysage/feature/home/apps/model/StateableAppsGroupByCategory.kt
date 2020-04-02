package com.chekh.paysage.feature.home.apps.model

data class StateableAppsGroupByCategory(
    val data: AppsGroupByCategory,
    var state: AppsDataViewState = AppsDataViewState()
)

data class AppsDataViewState(
    var isExpanded: Boolean = false,
    var scrollX: Int = 0
) {
    fun clear() {
        isExpanded = false
        scrollX = 0
    }
}