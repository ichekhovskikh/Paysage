package com.chekh.paysage.core.ui.anim.interpolator

import android.animation.TimeInterpolator
import android.view.animation.Interpolator

class ReverseInterpolator(
    private val delegate: TimeInterpolator,
    private val minValue: Float = 0f,
    private val maxValue: Float = 1f
) : Interpolator {

    override fun getInterpolation(input: Float): Float =
        maxValue + minValue - delegate.getInterpolation(input)
}
