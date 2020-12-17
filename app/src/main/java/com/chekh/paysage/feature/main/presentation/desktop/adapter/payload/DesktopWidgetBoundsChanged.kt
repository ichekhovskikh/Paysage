package com.chekh.paysage.feature.main.presentation.desktop.adapter.payload

import android.graphics.Rect
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel

data class DesktopWidgetBoundsChanged(val bounds: Rect)

fun isDesktopWidgetBoundsChanged(
    oldItem: DesktopWidgetModel,
    newItem: DesktopWidgetModel
) = oldItem.bounds != newItem.bounds
