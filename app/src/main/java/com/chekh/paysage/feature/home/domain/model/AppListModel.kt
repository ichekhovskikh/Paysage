package com.chekh.paysage.feature.home.domain.model

data class AppListModel(
    val apps: List<AppModel>,
    val appSize: Int,
    val appSpan: Int
)