package com.chekh.paysage.feature.main.presentation.options.anim

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import androidx.core.animation.doOnEnd

class DesktopOptionsOverlayAnimationFacade(
    container: View,
    vararg buttons: View
) {
    private val animators = mutableListOf<ObjectAnimator>()
    private var actionEnd: ((isReverse: Boolean) -> Unit)? = null
    private var isReverse = false

    init {
        val scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0f, 1f)
        val scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0f, 1f)
        val alpha = PropertyValuesHolder.ofFloat(View.ALPHA, 0f, 1f)

        animators.apply {
            add(ObjectAnimator.ofPropertyValuesHolder(container, alpha))
            buttons.forEach {
                add(ObjectAnimator.ofPropertyValuesHolder(it, scaleY, scaleX, alpha))
            }
        }
        animators.firstOrNull()?.doOnEnd {
            actionEnd?.invoke(isReverse)
        }
    }

    fun doOnEnd(action: (isReverse: Boolean) -> Unit) {
        actionEnd = action
    }

    fun start() {
        animate(isReverse = false)
    }

    fun reverse() {
        animate(isReverse = true)
    }

    fun cancel() {
        animators.forEach { it.cancel() }
    }

    private fun animate(isReverse: Boolean) {
        if (isRunning(isReverse = isReverse)) return
        this.isReverse = isReverse
        animators.forEach { animator ->
            animator.pause()
            if (isReverse) animator.reverse() else animator.start()
        }
    }

    private fun isRunning(isReverse: Boolean) =
        this.isReverse == isReverse && animators.firstOrNull()?.isRunning == true
}
