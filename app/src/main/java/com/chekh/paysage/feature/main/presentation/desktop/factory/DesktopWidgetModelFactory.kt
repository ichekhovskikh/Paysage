package com.chekh.paysage.feature.main.presentation.desktop.factory

import android.graphics.Rect
import com.chekh.paysage.common.data.model.DesktopWidgetType
import com.chekh.paysage.feature.main.domain.model.*
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import javax.inject.Inject

interface DesktopWidgetModelFactory {

    fun create(
        desktopWidgetId: String?,
        widget: WidgetModel?,
        pageId: Long,
        bounds: Rect,
        isDragging: Boolean = false
    ): DesktopWidgetModel
}

class DesktopWidgetModelFactoryImpl @Inject constructor() : DesktopWidgetModelFactory {

    override fun create(
        desktopWidgetId: String?,
        widget: WidgetModel?,
        pageId: Long,
        bounds: Rect,
        isDragging: Boolean
    ) = DesktopWidgetModel(
        id = desktopWidgetId.orEmpty(),
        packageName = widget?.packageName.orEmpty(),
        className = widget?.className.orEmpty(),
        label = widget?.label.orEmpty(),
        type = DesktopWidgetType.WIDGET,
        bounds = bounds,
        pageId = pageId,
        minHeight = widget?.minHeight ?: 0,
        minWidth = widget?.minWidth ?: 0,
        isDragging = isDragging,
        style = DesktopWidgetStyleModel.EMPTY
    )
}
