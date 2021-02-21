package com.chekh.paysage.feature.main.presentation.desktop.tools

import android.graphics.RectF
import com.chekh.paysage.feature.widget.domain.model.WidgetModel

internal fun WidgetModel.getWidgetBounds(location: RectF): RectF {
    val centerX = location.centerX()
    val centerY = location.centerY()
    val halfMinWidth = minWidth.toFloat() / 2
    val halfMinHeight = minHeight.toFloat() / 2
    return RectF(
        centerX - halfMinWidth,
        centerY - halfMinHeight,
        centerX + halfMinWidth,
        centerY + halfMinHeight
    )
}
