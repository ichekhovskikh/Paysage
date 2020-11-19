package com.chekh.paysage.feature.main.presentation.home.anim

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.core.animation.doOnEnd

class OverlayHomeButtonsAnimationFacade(
    blBackgroundBlur: View,
    tvWallpaper: View,
    tvWidgets: View,
    tvSettings: View
) {
    private val animators = mutableListOf<ObjectAnimator>()
    private var actionEnd: (() -> Unit)? = null

    init {
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 1f)
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)

        animators.apply {
            add(ObjectAnimator.ofPropertyValuesHolder(blBackgroundBlur, alpha))
            add(ObjectAnimator.ofPropertyValuesHolder(tvWallpaper, scaleY, scaleX, alpha))
            add(ObjectAnimator.ofPropertyValuesHolder(tvWidgets, scaleY, scaleX, alpha))
            add(ObjectAnimator.ofPropertyValuesHolder(tvSettings, scaleY, scaleX, alpha))
        }
        animators.firstOrNull()?.doOnEnd { actionEnd?.invoke() }
    }

    fun start(isReverse: Boolean = false) {
        if (isReverse) {
            animators.forEach { it.reverse() }
        } else {
            animators.forEach { it.start() }
        }
    }

    fun cancel() {
        animators.forEach { it.cancel() }
    }

    fun doOnEnd(action: () -> Unit) {
        actionEnd = action
    }
}
