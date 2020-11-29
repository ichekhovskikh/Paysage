package com.chekh.paysage.feature.main.domain.model

import com.chekh.paysage.common.data.model.DesktopWidgetType

data class DesktopWidgetModel(
    val id: String,
    val packageName: String,
    val className: String,
    val label: String,
    val type: DesktopWidgetType,
    val x: Int,
    val y: Int,
    val height: Int,
    val width: Int,
    val minHeight: Int,
    val minWidth: Int,
    val style: DesktopWidgetStyleModel? = null
)

data class DesktopWidgetStyleModel(
    val color: Int = 0,
    val alpha: Int = 0,
    val corner: Int = 0
)
