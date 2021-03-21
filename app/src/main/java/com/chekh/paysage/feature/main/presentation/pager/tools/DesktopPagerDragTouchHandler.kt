package com.chekh.paysage.feature.main.presentation.pager.tools

import android.graphics.PointF
import android.graphics.Rect
import kotlinx.coroutines.*
import javax.inject.Inject

interface DesktopPagerDragTouchHandler {

    fun init(bounds: Rect)
    fun setOnTouchPageChanged(listener: (Int) -> Unit)
    fun handleDragTouch(touch: PointF, page: Int)
    fun stopHandleDragTouch()
}

class DesktopPagerDragTouchHandlerImpl @Inject constructor() : DesktopPagerDragTouchHandler {

    private var bounds: Rect? = null
    private var delayedJob: Job? = null
    private var onTouchPageChangedListener: ((Int) -> Unit)? = null

    override fun init(bounds: Rect) {
        this.bounds = bounds
    }

    override fun setOnTouchPageChanged(listener: (Int) -> Unit) {
        onTouchPageChangedListener = listener
    }

    override fun handleDragTouch(touch: PointF, page: Int) {
        val pageIncrement = calculatePageIncrement(touch)
        if (pageIncrement == 0) {
            stopHandleDragTouch()
            return
        }
        if (delayedJob == null || delayedJob?.isCompleted == true) {
            delayedJob = GlobalScope.launch {
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

    private fun calculatePageIncrement(touch: PointF): Int {
        val bounds = bounds ?: return 0
        return when {
            touch.y < bounds.top + SWITCH_INTERVAL -> -1
            touch.y > bounds.bottom - SWITCH_INTERVAL -> 1
            else -> 0
        }
    }

    private companion object {
        const val TOUCH_PAGE_CHANGED_DELAY = 1000L
        const val SWITCH_INTERVAL = 30
    }
}
