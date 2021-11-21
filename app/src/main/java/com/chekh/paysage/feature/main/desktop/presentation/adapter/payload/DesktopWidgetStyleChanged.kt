package com.chekh.paysage.feature.main.desktop.presentation.adapter.payload

import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetStyleModel

data class DesktopWidgetStyleChanged(val style: DesktopWidgetStyleModel)

fun isDesktopWidgetStyleChanged(
    oldItem: DesktopWidgetModel,
    newItem: DesktopWidgetModel
) = oldItem.style != newItem.style
