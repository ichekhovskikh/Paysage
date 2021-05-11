package com.chekh.paysage.core.ui.view

import android.content.Context
import android.graphics.PointF
import android.util.AttributeSet
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import androidx.cardview.widget.CardView
import com.chekh.paysage.core.provider.ui
import kotlinx.coroutines.*
import kotlin.math.abs

class LongClickableCardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    private var onLongClickListener: OnLongClickListener? = null
    private var delayedJob: Job? = null
    private var downPoint = PointF(0f, 0f)

    init {
        isLongClickable = true
        isClickable = true
        isFocusable = true
    }

    override fun setOnLongClickListener(
        onLongClickListener: OnLongClickListener?
    ) {
        this.onLongClickListener = onLongClickListener
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (onLongClickListener == null) {
            return super.onInterceptTouchEvent(event)
        }
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                downPoint = PointF(event.x, event.y)
                if (delayedJob == null || delayedJob?.isCompleted == true) {
                    delayedJob = GlobalScope.launch(ui) {
                        delay(CLICK_DURATION)
                        onLongClickListener?.let {
                            performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                            it.onLongClick(this@LongClickableCardView)
                        }
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (delayedJob?.isActive == true && isOverMove(event.x, event.y)) {
                    delayedJob?.cancel()
                }
            }
            else -> delayedJob?.cancel()
        }
        return false
    }

    private fun isOverMove(moveX: Float, moveY: Float) =
        abs(downPoint.x - moveX) > AVAILABLE_MOVE_PX ||
            abs(downPoint.y - moveY) > AVAILABLE_MOVE_PX

    private companion object {
        const val CLICK_DURATION = 500L
        const val AVAILABLE_MOVE_PX = 5
    }
}
