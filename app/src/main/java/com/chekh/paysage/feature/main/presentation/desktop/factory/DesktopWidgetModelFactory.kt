package com.chekh.paysage.feature.main.presentation.desktop.factory

import android.graphics.RectF
import com.chekh.paysage.common.data.model.DesktopWidgetType
import com.chekh.paysage.feature.main.domain.model.*
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import javax.inject.Inject

interface DesktopWidgetModelFactory {

    fun create(
        desktopWidgetId: Int,
        widget: WidgetModel,
        bounds: RectF
    ): DesktopWidgetModel
}

class DesktopWidgetModelFactoryImpl @Inject constructor() : DesktopWidgetModelFactory {

    override fun create(
        desktopWidgetId: Int,
        widget: WidgetModel,
        bounds: RectF
    ) = DesktopWidgetModel(
        id = desktopWidgetId.toString(),
        packageName = widget.packageName,
        className = widget.className,
        label = widget.label,
        type = DesktopWidgetType.WIDGET,
        x = bounds.left.toInt(),
        y = bounds.top.toInt(),
        height = widget.minHeight,
        width = widget.minWidth,
        minHeight = widget.minHeight,
        minWidth = widget.minWidth,
        style = null
    )
}
