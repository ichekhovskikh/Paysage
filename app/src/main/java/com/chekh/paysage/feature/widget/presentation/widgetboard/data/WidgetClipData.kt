package com.chekh.paysage.feature.widget.presentation.widgetboard.data

import com.chekh.paysage.common.data.model.DesktopWidgetType
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetStyleModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel

data class WidgetClipData(
    val id: String?,
    val packageName: String,
    val className: String,
    val label: String,
    val height: Int,
    val width: Int,
    val type: DesktopWidgetType,
    val minHeight: Int,
    val minWidth: Int,
    val style: DesktopWidgetStyleModel
) : ClipData

fun WidgetModel.toClipData() = WidgetClipData(
    id = null,
    packageName = packageName,
    className = className,
    label = label,
    height = minHeight,
    width = minWidth,
    type = DesktopWidgetType.WIDGET,
    minHeight = minHeight,
    minWidth = minWidth,
    style = DesktopWidgetStyleModel.EMPTY
)
