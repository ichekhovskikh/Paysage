package com.chekh.paysage.core.ui.tools

import android.graphics.RectF
import android.view.View

enum class RelativePosition {
    INSIDE,
    OUTSIDE_START,
    OUTSIDE_END,
    OUTSIDE_TOP,
    OUTSIDE_BOTTOM
}

fun View.getRelativePosition(rect: RectF): RelativePosition? {
    val xy = IntArray(2)
    getLocationOnScreen(xy)

    val (viewStart, viewTop) = xy
    val viewEnd = viewStart + width
    val viewBottom = viewTop + height

    val rectStart = rect.left
    val rectEnd = rect.right
    val rectTop = rect.top
    val rectBottom = rect.bottom

    return when {
        rectStart < viewStart -> RelativePosition.OUTSIDE_START
        rectEnd > viewEnd -> RelativePosition.OUTSIDE_END
        rectTop < viewTop -> RelativePosition.OUTSIDE_TOP
        rectBottom > viewBottom -> RelativePosition.OUTSIDE_BOTTOM
        else -> RelativePosition.INSIDE
    }
}
