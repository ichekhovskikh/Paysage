package com.chekh.paysage.core.ui.anim

import android.animation.ValueAnimator
import android.view.View

class JumpAnimation(val view: View) {

    private val anim: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            val height = view.layoutParams.height / 4
            if (animatedValue <= 0.5) {
                view.translationY = height * animatedValue
            } else {
                view.translationY = height * (1 - animatedValue)
            }
        }
    }

    fun jump() {
        anim.cancel()
        anim.start()
    }
}
