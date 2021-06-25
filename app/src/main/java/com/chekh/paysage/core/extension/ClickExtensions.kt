package com.chekh.paysage.core.extension

import android.annotation.SuppressLint
import android.view.*
import android.view.MotionEvent.*
import com.chekh.paysage.core.ui.listener.SimpleGestureListener
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
fun View.setOnGestureLongPress(listener: (MotionEvent?) -> Unit) {
    isLongClickable = true
    var hasMoveBeforeCall = true
    var prevX = -1f
    var prevY = -1f
    val longPressGestureDetector = GestureDetector(
        context,
        object : SimpleGestureListener {
            override fun onLongPress(e: MotionEvent?) {
                if (!hasMoveBeforeCall) {
                    performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    setHierarchyViewPagerTouching(false)
                    listener(e)
                }
            }
        }
    )
    setOnTouchListener { _, event ->
        when (event.action) {
            ACTION_DOWN -> {
                hasMoveBeforeCall = false
                prevX = event.x
                prevY = event.y
            }
            ACTION_CANCEL, ACTION_UP -> {
                setHierarchyViewPagerTouching(true)
            }
        }
        if (abs(prevX - event.x) > AVAILABLE_MOVE_PX ||
            abs(prevY - event.y) > AVAILABLE_MOVE_PX
        ) {
            hasMoveBeforeCall = true
        }

        return@setOnTouchListener longPressGestureDetector.onTouchEvent(event)
    }
}
