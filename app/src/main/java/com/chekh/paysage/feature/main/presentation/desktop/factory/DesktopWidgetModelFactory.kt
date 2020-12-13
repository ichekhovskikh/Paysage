package com.chekh.paysage.feature.main.presentation.desktop.factory

import android.graphics.Rect
import android.graphics.RectF
import androidx.core.graphics.toRect
import com.chekh.paysage.common.data.model.DesktopWidgetType
import com.chekh.paysage.feature.main.domain.model.*
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import javax.inject.Inject

interface DesktopWidgetModelFactory {

    fun create(
        desktopWidgetId: String?,
        widget: WidgetModel?,
        bounds: RectF,
        isDragging: Boolean = false
    ): DesktopWidgetModel
}

class DesktopWidgetModelFactoryImpl @Inject constructor() : DesktopWidgetModelFactory {

    override fun create(
        desktopWidgetId: String?,
        widget: WidgetModel?,
        bounds: RectF,
        isDragging: Boolean
    ): DesktopWidgetModel {
        val minWidth = widget?.minWidth ?: 0
        val minHeight = widget?.minHeight ?: 0
        val centerX = bounds.centerX()
        val centerY = bounds.centerY()
        val widgetBounds = when (widget) {
            null -> bounds.toRect()
            else -> Rect(
                (centerX - minWidth.toFloat() / 2).toInt(),
                (centerY - minHeight.toFloat() / 2).toInt(),
                (centerX + minWidth.toFloat() / 2).toInt(),
                (centerY + minHeight.toFloat() / 2).toInt(),
            )
        }
        return DesktopWidgetModel(
            id = desktopWidgetId.orEmpty(),
            packageName = widget?.packageName.orEmpty(),
            className = widget?.className.orEmpty(),
            label = widget?.label.orEmpty(),
            type = DesktopWidgetType.WIDGET,
            bounds = widgetBounds,
            minHeight = minHeight,
            minWidth = minWidth,
            isDragging = isDragging,
            style = DesktopWidgetStyleModel.EMPTY
        )
    }
}
