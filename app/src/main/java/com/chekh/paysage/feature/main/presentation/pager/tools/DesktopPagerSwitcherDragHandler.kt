package com.chekh.paysage.feature.main.presentation.pager.tools

import android.graphics.PointF
import android.graphics.Rect
import com.chekh.paysage.core.provider.back
import com.chekh.paysage.core.ui.tools.ActionDelay
import kotlinx.coroutines.*
import javax.inject.Inject

interface DesktopPagerSwitcherDragHandler {

    fun setOnTouchPageChanged(listener: (Int) -> Unit)
    fun handleDragTouch(touch: PointF, page: Int, pageBounds: Rect)
    fun stopHandleDragTouch()
}

class DesktopPagerSwitcherDragHandlerImpl @Inject constructor() : DesktopPagerSwitcherDragHandler {

    private var delay = ActionDelay()
    private var onTouchPageChangedListener: ((Int) -> Unit)? = null

    override fun setOnTouchPageChanged(listener: (Int) -> Unit) {
        onTouchPageChangedListener = listener
    }

    override fun handleDragTouch(touch: PointF, page: Int, pageBounds: Rect) {
        val pageIncrement = calculatePageIncrement(touch, pageBounds)
        if (pageIncrement == 0) {
            stopHandleDragTouch()
            return
        }
        delay.start(back, TOUCH_PAGE_CHANGED_DELAY) {
            var nextPage = page + pageIncrement
            while (isActive) {
                onTouchPageChangedListener?.invoke(nextPage)
                nextPage += pageIncrement
            }
        }
    }

    override fun stopHandleDragTouch() {
        delay.cancel()
    }

    private fun calculatePageIncrement(touch: PointF, bounds: Rect) = when {
        touch.y < bounds.top + SWITCH_INTERVAL -> -1
        touch.y > bounds.bottom - SWITCH_INTERVAL -> 1
        else -> 0
    }

    private companion object {
        const val TOUCH_PAGE_CHANGED_DELAY = 800L
        const val SWITCH_INTERVAL = 30
    }
}
