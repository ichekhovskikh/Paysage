package com.chekh.paysage.ui.view.core.smoothscroll

import android.content.Context
import android.graphics.PointF

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller

class TopSmoothScroller(
    context: Context,
    private val layoutManager: LinearLayoutManager
) : LinearSmoothScroller(context) {

    private val vectorPosition = PointF(0f, 0f)

    override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
        val firstChildPos = layoutManager.findFirstCompletelyVisibleItemPosition()
        val direction = if (targetPosition < firstChildPos) -1 else 1

        return if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
            vectorPosition.apply { set(direction.toFloat(), 0f) }
        } else {
            vectorPosition.apply { set(0f, direction.toFloat()) }
        }
    }

    override fun calculateTimeForScrolling(dx: Int): Int {
        return SCROLL_DURATION
    }

    companion object {
        var SCROLL_DURATION = 50
    }
}