package com.chekh.paysage.feature.main.domain.model

import android.graphics.Point

data class DesktopWidgetModel(
    val id: String,
    val title: String,
    val position: Point,
    val width: Int,
    val height: Int,
    val style: DesktopWidgetStyleModel? = null
)

data class DesktopWidgetStyleModel(
    val radius: Int,
    val elevation: Int,
    val color: Int
)
