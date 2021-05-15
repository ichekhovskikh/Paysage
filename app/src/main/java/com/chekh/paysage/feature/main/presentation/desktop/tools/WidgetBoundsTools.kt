package com.chekh.paysage.feature.main.presentation.desktop.tools

import android.graphics.RectF

internal fun getWidgetBounds(location: RectF, width: Int, height: Int): RectF {
    val centerX = location.centerX()
    val centerY = location.centerY()
    val halfWidth = width.toFloat() / 2
    val halfHeight = height.toFloat() / 2
    return RectF(
        centerX - halfWidth,
        centerY - halfHeight,
        centerX + halfWidth,
        centerY + halfHeight
    )
}
