package com.chekh.paysage.core.handler.slide

import android.view.View
import androidx.core.view.isGone
import com.chekh.paysage.core.extension.absoluteHeight
import com.chekh.paysage.core.extension.setMarginTop
import com.chekh.paysage.core.ui.anim.JumpAnimation

class AppsBoardSlideHandler(private val dock: View, private val panel: View) {

    private val animation = JumpAnimation(dock)
    private var expandedMarginTop: Int = 0

    fun setExpandedMarginTop(insetTop: Int) {
        this.expandedMarginTop = insetTop
    }

    fun slideToTop(offset: Float, anchor: Float) {
        if (offset <= anchor) {
            slideToHalfExpanded(offset / anchor)
        } else {
            slideToFullExpanded((1 - offset) / (1 - anchor))
        }
    }

    private fun slideToHalfExpanded(value: Float) {
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

    private fun slideToFullExpanded(value: Float) {
        val marginTop = (expandedMarginTop * (1 - value)).toInt()
        panel.setMarginTop(marginTop)
    }

    private companion object {
        const val MAX_VALUE = 1f
        const val MIN_VALUE = 0f
    }
}
