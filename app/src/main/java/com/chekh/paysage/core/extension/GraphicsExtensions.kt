package com.chekh.paysage.core.extension

import android.graphics.Rect
import android.graphics.RectF

fun Rect.isIntersect(other: Rect) =
    intersects(other.left, other.top, other.right, other.bottom)

fun RectF.copy(
    left: Float = this.left,
    top: Float = this.top,
    right: Float = this.right,
    bottom: Float = this.bottom,
) = RectF(left, top, right, bottom)

fun Rect.copy(
    left: Int = this.left,
    top: Int = this.top,
    right: Int = this.right,
    bottom: Int = this.bottom,
) = Rect(left, top, right, bottom)

fun Rect.copyOffset(
    horizontalOffset: Int = 0,
    verticalOffset: Int = 0
) = copy(
    left + horizontalOffset,
    top + verticalOffset,
    right + horizontalOffset,
    bottom + verticalOffset
)
