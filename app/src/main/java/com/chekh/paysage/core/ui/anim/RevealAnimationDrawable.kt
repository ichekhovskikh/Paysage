package com.chekh.paysage.core.ui.anim

import android.graphics.Canvas
import android.graphics.Paint
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.Interpolator
import com.chekh.paysage.core.ui.tools.createColorAlpha

class RevealAnimationDrawable(
    private val x: Float,
    private val y: Float,
    private val startRadius: Int,
    private val endRadius: Int,
    basicColor: Int
) {
    private val color: Int
    private val paint: Paint = Paint().apply { isAntiAlias = true }
    private var animationListener: RevealAnimationListener? = null
    private var totalTime: Int = 0
    private var startTime: Long = 0

    var duration = ANIMATION_DURATION_DEFAULT
    var interpolator: Interpolator = AccelerateDecelerateInterpolator()
    var isAnimating: Boolean = false
        private set

    init {
        color = createColorAlpha(basicColor, DEFAULT_ALPHA)
    }

    fun start() {
        isAnimating = true
        totalTime = 0
        startTime = AnimationUtils.currentAnimationTimeMillis()
    }

    fun cancel() {
        isAnimating = false
        animationListener?.onRevealAnimationCancel()
    }

    /**
     * How to use for View (Example):
     *
     * override fun dispatchDraw(canvas: Canvas) {
     *      super.dispatchDraw(canvas)
     *      if (revealDrawable != null && revealDrawable.isAnimating) {
     *          revealDrawable.drawAnimation(canvas)
     *          invalidate()
     *      }
     * }
     */
    fun drawAnimation(canvas: Canvas) {
        if (!isAnimating) return
        if (totalTime < duration) {
            if (totalTime == 0) {
                animationListener?.onRevealAnimationStart()
            }
            totalTime = (AnimationUtils.currentAnimationTimeMillis() - startTime).toInt()
            val animValue = getAnimValue()
            val invAnimValue = 1.0f - animValue
            paint.color = createColorAlpha(color, invAnimValue)
            val radius = startRadius * invAnimValue + endRadius * animValue
            canvas.drawCircle(x, y, radius, paint)
            animationListener?.onChangedAnimationValue(animValue)
        } else {
            isAnimating = false
            animationListener?.onRevealAnimationEnd()
        }
    }

    private fun getAnimValue(): Float {
        return interpolator.getInterpolation(totalTime.toFloat() / duration)
    }

    fun setRevealListener(animationListener: RevealAnimationListener) {
        this.animationListener = animationListener
    }

    interface RevealAnimationListener {
        fun onRevealAnimationStart()
        fun onChangedAnimationValue(value: Float)
        fun onRevealAnimationCancel()
        fun onRevealAnimationEnd()
    }

    companion object {
        private const val ANIMATION_DURATION_DEFAULT = 200
        private const val DEFAULT_ALPHA = 0.2f
    }
}
