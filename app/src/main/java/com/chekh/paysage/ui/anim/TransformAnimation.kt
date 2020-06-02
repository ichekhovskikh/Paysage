package com.chekh.paysage.ui.anim

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT

class TransformAnimation(
    val view: View,
    var duration: Long = ANIMATION_DURATION_DEFAULT
) {

    var onAnimationCancelListener: (() -> Unit)? = null

    fun transform(from: Int, to: Int) {
        val anim = ValueAnimator.ofInt(from, to)
        anim.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                val layoutParams = view.layoutParams
                layoutParams.height = WRAP_CONTENT
                view.layoutParams = layoutParams
                onAnimationCancelListener?.invoke()
            }
        })
        anim.duration = duration
        anim.start()
    }

    companion object {
        private const val ANIMATION_DURATION_DEFAULT = 250L
    }
}