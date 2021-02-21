package com.chekh.paysage.feature.main.domain.model

import android.graphics.Color
import android.graphics.Rect
import com.chekh.paysage.common.data.model.DesktopWidgetType

data class DesktopWidgetModel(
    val id: String,
    val packageName: String,
    val className: String,
    val label: String,
    val type: DesktopWidgetType,
    val bounds: Rect,
    val page: Int,
    val minHeight: Int,
    val minWidth: Int,
    val style: DesktopWidgetStyleModel,
    val isDragging: Boolean = false
)

data class DesktopWidgetStyleModel(
    val color: Int = Color.WHITE,
    val alpha: Float = 0f,
    val elevation: Int = 0,
    val corner: Int = 0
) {
    companion object {
        val EMPTY = DesktopWidgetStyleModel()
    }
}
