package com.chekh.paysage.handler.slide

import android.view.View
import androidx.core.view.isGone
import com.chekh.paysage.extension.absoluteHeight
import com.chekh.paysage.extension.setMarginTop
import com.chekh.paysage.ui.anim.JumpAnimation

class DockBarSlideHandler(private val dock: View, private val panel: View) {

    private val animation = JumpAnimation(dock)

    fun slideToTop(value: Float) {
        val inverseValue = 1f - value
        dock.alpha = inverseValue
        val marginTop = (dock.absoluteHeight * inverseValue).toInt()
        panel.setMarginTop(marginTop)
        dock.isGone = value >= MAX_VALUE
        panel.isGone = value == MIN_VALUE
        if (value == MIN_VALUE) {
            animation.jump()
        }
    }

    companion object {
        private const val MAX_VALUE = 0.99f
        private const val MIN_VALUE = 0f
    }
}