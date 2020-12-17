package com.chekh.paysage.feature.main.presentation.desktop.adapter.payload

import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetStyleModel

data class DesktopWidgetStyleChanged(val style: DesktopWidgetStyleModel)

fun isDesktopWidgetStyleChanged(
    oldItem: DesktopWidgetModel,
    newItem: DesktopWidgetModel
) = oldItem.style != newItem.style
