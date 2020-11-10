package com.chekh.paysage.core.extension

import android.annotation.SuppressLint
import android.view.*
import android.view.MotionEvent.ACTION_DOWN
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chekh.paysage.core.ui.listener.SimpleGestureListener
import com.chekh.paysage.core.ui.listener.SimpleScaleGestureListener
import com.chekh.paysage.core.ui.tools.OnDebounceClickListener
import kotlin.math.abs

private const val AVAILABLE_MOVE_PX = 5

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction?) {
    beginTransaction().func()?.commit()
}

fun View.onClick(listener: View.OnClickListener?) =
    listener?.let { setOnClickListener(OnDebounceClickListener(it)) }

fun View.onClick(listener: (() -> Unit)?) =
    listener?.let { action -> onClick(View.OnClickListener { action() }) }

fun View.onVibrateClick(listener: (() -> Unit)?) =
    listener?.let { action ->
        onClick {
            performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
            action()
        }
    }

fun View.setMarginTop(top: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.topMargin = top
    layoutParams = params
}

fun View.setMarginBottom(bottom: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.bottomMargin = bottom
    layoutParams = params
}

fun View.setHeight(height: Int) {
    layoutParams = layoutParams.apply { this.height = height }
}

inline val View.absoluteHeight: Int
    get() = measuredHeight + marginTop + marginBottom

fun View.applyPadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnGestureScaleAndLongPress(listener: () -> Unit) {
    var hasDownBeforeCall = false
    var hasMoveBeforeCall = false
    var prevX = -1f
    var prevY = -1f
    val longPressGestureDetector = GestureDetector(
        context,
        object : SimpleGestureListener {
            override fun onLongPress(e: MotionEvent) {
                if (!hasMoveBeforeCall) {
                    performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    listener()
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
                    listener()
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
        prevX = event.x
        prevY = event.y

        longPressGestureDetector.onTouchEvent(event) || scaleGestureDetector.onTouchEvent(event)
        return@setOnTouchListener false
    }
}
