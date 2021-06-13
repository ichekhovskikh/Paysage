package com.chekh.paysage.core.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import com.chekh.paysage.core.ui.anim.RevealAnimationDrawable
import kotlin.math.max

class RevealAnimationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    @ColorInt
    var animationColor: Int = Color.BLACK

    var startRadius: Int? = null

    var endRadius: Int? = null

    private var revealDrawable: RevealAnimationDrawable? = null

    fun animateRipple(x: Float, y: Float) {
        val startRadius = startRadius ?: 0
        val endRadius = endRadius ?: max(width, height)
        revealDrawable = RevealAnimationDrawable(x, y, startRadius, endRadius, animationColor)
        revealDrawable?.start()
        invalidate()
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        val revealDrawable = revealDrawable ?: return
        if (revealDrawable.isAnimating) {
            revealDrawable.drawAnimation(canvas)
            invalidate()
        }
    }
}
