package com.chekh.paysage.core.ui.anim

import android.animation.Animator
import android.graphics.Point
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.isVisible
import com.chekh.paysage.core.ui.anim.interpolator.ReverseInterpolator
import kotlin.math.hypot

class CircularRevealAnimation(
    private val view: View,
    private val duration: Long = DEFAULT_DURATION
) {
    private val interpolator = AccelerateDecelerateInterpolator()
    private val reverseInterpolator = ReverseInterpolator(interpolator)
    private var actionEnd: ((isReverse: Boolean) -> Unit)? = null
    private var lastCenter: Point? = null
    private var isReverse = false

    private var animator: Animator? = null

    fun doOnEnd(action: (isReverse: Boolean) -> Unit) {
        actionEnd = action
    }

    fun start(center: Point) {
        lastCenter = center
        animate(center, isReverse = false)
    }

    fun reverse() {
        lastCenter?.let { animate(it, isReverse = true) }
    }

    fun cancel() {
        animator?.cancel()
    }

    private fun animate(center: Point, isReverse: Boolean) {
        if (isRunning(isReverse = isReverse)) return
        this.isReverse = isReverse
        val radius = hypot(view.measuredHeight.toDouble(), view.measuredWidth.toDouble())
        animator = ViewAnimationUtils.createCircularReveal(
            view,
            center.x,
            center.y,
            0f,
            radius.toFloat()
        ).apply {
            duration = this@CircularRevealAnimation.duration
            interpolator = if (isReverse) reverseInterpolator else interpolator
            doOnStart { view.isVisible = true }
            doOnEnd {
                view.isVisible = !isReverse
                actionEnd?.invoke(isReverse)
            }
        }
        animator?.start()
    }

    private fun isRunning(isReverse: Boolean) =
        this.isReverse == isReverse && animator?.isRunning == true

    private companion object {
        const val DEFAULT_DURATION = 100L
    }
}
