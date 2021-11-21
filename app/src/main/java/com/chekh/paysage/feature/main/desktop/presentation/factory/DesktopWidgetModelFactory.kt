package com.chekh.paysage.feature.main.desktop.presentation.factory

import android.graphics.Rect
import com.chekh.paysage.feature.main.common.domain.model.*
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
import javax.inject.Inject

interface DesktopWidgetModelFactory {

    fun create(
        widget: WidgetClipData,
        pageId: Long,
        bounds: Rect,
        isDragging: Boolean = true
    ): DesktopWidgetModel
}

class DesktopWidgetModelFactoryImpl @Inject constructor() : DesktopWidgetModelFactory {

    override fun create(
        widget: WidgetClipData,
        pageId: Long,
        bounds: Rect,
        isDragging: Boolean
    ) = DesktopWidgetModel(
        id = widget.id.orEmpty(),
        packageName = widget.packageName,
        className = widget.className,
        label = widget.label,
        type = widget.type,
        bounds = bounds,
        pageId = pageId,
        minHeight = widget.minHeight,
        minWidth = widget.minWidth,
        style = widget.style,
        isDragging = isDragging
    )
}
