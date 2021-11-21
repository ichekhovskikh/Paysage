package com.chekh.paysage.feature.main.common.domain.model

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
    val pageId: Long,
    val minHeight: Int,
    val minWidth: Int,
    val style: DesktopWidgetStyleModel,
    val isDragging: Boolean = false
)

data class DesktopWidgetStyleModel(
    val color: Int,
    val alpha: Float,
    val elevation: Int,
    val corner: Int
) {
    companion object {
        private const val DEFAULT_COLOR = Color.WHITE
        private const val DEFAULT_ALPHA = 0f
        private const val DEFAULT_ELEVATION = 0
        private const val DEFAULT_CORNER = 24

        val EMPTY = DesktopWidgetStyleModel(
            DEFAULT_COLOR,
            DEFAULT_ALPHA,
            DEFAULT_ELEVATION,
            DEFAULT_CORNER
        )
    }
}
