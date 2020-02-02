package com.chekh.paysage.ui.anim

import android.view.View
import android.view.ViewGroup
import android.view.animation.*

class TransformAnimation(
    val view: View,
    var duration: Long = DURATION_DEFAULT
) {

    fun transform(from: Int, to: Int, onCancel: (() -> Unit)? = null) {
        val distance = to - from
        view.visibility = View.VISIBLE
        val anim = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                val interpolatedDistance = (distance * interpolatedTime).toInt()
                view.layoutParams.height = if (interpolatedTime == 1f) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else {
                    from + interpolatedDistance + 1
                }
                view.requestLayout()
                if (interpolatedTime == 1f) {
                    onCancel?.invoke()
                }
            }

            override fun willChangeBounds() = true
        }
        anim.duration = duration
        view.startAnimation(anim)
    }

    companion object {
        private const val DURATION_DEFAULT = 50L
    }
}