package com.chekh.paysage.handler.slide

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import com.chekh.paysage.ui.anim.JumpAnimation

class DockBarSlideHandler(private val dockBar: View) {

    private var initialHeight: Int = dockBar.height
    private val margin: Int = dockBar.marginBottom

    private val animation = JumpAnimation(dockBar)

    fun viewHeightChanged() {
        initialHeight = dockBar.layoutParams.height
    }

    fun slideToTop(value: Float) {
        val inverseValue = 1f - value
        dockBar.alpha = inverseValue
        val layoutParams = dockBar.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.height = (initialHeight * inverseValue).toInt()
        layoutParams.bottomMargin = (margin * inverseValue).toInt()
        layoutParams.topMargin = (margin * inverseValue).toInt()
        dockBar.layoutParams = layoutParams
        dockBar.visibility = if (value >= 1f) View.GONE else View.VISIBLE
        if (value == 0f) {
            animation.jump()
        }
    }
}