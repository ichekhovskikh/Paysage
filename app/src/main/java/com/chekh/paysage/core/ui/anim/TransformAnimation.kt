package com.chekh.paysage.core.ui.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.view.updateLayoutParams

class TransformAnimation(
    val view: View
) {

    var onAnimationCancelListener: (() -> Unit)? = null

    fun transform(from: Int, to: Int, duration: Long = ANIMATION_DURATION_DEFAULT) {
        val anim = ValueAnimator.ofInt(from, to)
        anim.addUpdateListener { valueAnimator ->
            view.updateLayoutParams {
                height = valueAnimator.animatedValue as Int
            }
        }
        anim.addListener(
            object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                    view.updateLayoutParams { height = WRAP_CONTENT }
                    onAnimationCancelListener?.invoke()
                }
            }
        )
        anim.duration = duration
        anim.start()
    }

    private companion object {
        const val ANIMATION_DURATION_DEFAULT = 250L
    }
}
