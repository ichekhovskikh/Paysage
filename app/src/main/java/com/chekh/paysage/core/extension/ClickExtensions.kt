package com.chekh.paysage.core.extension

import android.annotation.SuppressLint
import android.view.*
import android.view.MotionEvent.ACTION_DOWN
import com.chekh.paysage.core.ui.listener.SimpleGestureListener
import com.chekh.paysage.core.ui.listener.SimpleScaleGestureListener
import com.chekh.paysage.core.ui.tools.OnDebounceClickListener
import kotlin.math.abs

private const val AVAILABLE_MOVE_PX = 5

fun View.onClick(listener: View.OnClickListener?) =
    listener?.let { setOnClickListener(OnDebounceClickListener(it)) }

fun View.onClick(listener: (() -> Unit)?) =
    listener?.let { action -> onClick(View.OnClickListener { action() }) }

fun View.onVibrateClick(
    feedbackConstant: Int = HapticFeedbackConstants.VIRTUAL_KEY,
    listener: (() -> Unit)?
) = listener?.let { action ->
    onClick {
        performHapticFeedback(feedbackConstant)
        action()
    }
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnGestureScaleAndLongPress(listener: (MotionEvent?) -> Unit) {
    isLongClickable = true
    var hasDownBeforeCall = false
    var hasMoveBeforeCall = true
    var prevX = -1f
    var prevY = -1f
    val longPressGestureDetector = GestureDetector(
        context,
        object : SimpleGestureListener {
            override fun onLongPress(e: MotionEvent?) {
                if (!hasMoveBeforeCall) {
                    performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    listener(e)
                }
            }
        }
    )
    val scaleGestureDetector = ScaleGestureDetector(
        context,
        object : SimpleScaleGestureListener {
            override fun onScaleEnd(detector: ScaleGestureDetector) {
                if (hasDownBeforeCall) {
                    performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    listener(null)
                    hasDownBeforeCall = false
                }
            }
        }
    )
    setOnTouchListener { _, event ->
        if (event.action == ACTION_DOWN) {
            hasMoveBeforeCall = false
            hasDownBeforeCall = true
            prevX = event.x
            prevY = event.y
        }
        if (abs(prevX - event.x) > AVAILABLE_MOVE_PX ||
            abs(prevY - event.y) > AVAILABLE_MOVE_PX
        ) {
            hasMoveBeforeCall = true
        }

        longPressGestureDetector.onTouchEvent(event) || scaleGestureDetector.onTouchEvent(event)
        return@setOnTouchListener true
    }
}
