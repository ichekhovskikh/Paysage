package com.chekh.paysage.feature.main.presentation.pager.tools

import android.graphics.PointF
import android.graphics.Rect
import com.chekh.paysage.core.provider.back
import kotlinx.coroutines.*
import javax.inject.Inject

interface DesktopPagerSwitcherDragHandler {

    fun setOnTouchPageChanged(listener: (Int) -> Unit)
    fun handleDragTouch(touch: PointF, page: Int, pageBounds: Rect)
    fun stopHandleDragTouch()
}

class DesktopPagerSwitcherDragHandlerImpl @Inject constructor() : DesktopPagerSwitcherDragHandler {

    private var delayedJob: Job? = null
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
        if (delayedJob == null || delayedJob?.isCompleted == true) {
            delayedJob = GlobalScope.launch(back) {
                var nextPage = page + pageIncrement
                while (isActive) {
                    delay(TOUCH_PAGE_CHANGED_DELAY)
                    onTouchPageChangedListener?.invoke(nextPage)
                    nextPage += pageIncrement
                }
            }
        }
    }

    override fun stopHandleDragTouch() {
        delayedJob?.cancel()
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
