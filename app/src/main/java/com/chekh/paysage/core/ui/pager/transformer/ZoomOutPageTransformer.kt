package com.chekh.paysage.core.ui.pager.transformer

import android.view.View
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.abs

class ZoomOutPageTransformer(
    private val minScale: Float = MIN_SCALE,
    private val minAlpha: Float = MIN_ALPHA
) : ViewPager2.PageTransformer {

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        when {
            position < -1 -> {
                // This page is way off-screen to the left.
                view.alpha = 0f
            }
            position <= 1 -> {
                // Modify the default slide transition to shrink the page as well
                val scaleFactor = minScale.coerceAtLeast(1 - abs(position))
                val vertMargin = pageHeight * (1 - scaleFactor) / 2
                val horzMargin = pageWidth * (1 - scaleFactor) / 2
                if (position < 0) {
                    view.translationX = horzMargin - vertMargin / 2
                } else {
                    view.translationX = -horzMargin + vertMargin / 2
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor

                // Fade the page relative to its size.
                view.alpha = minAlpha +
                    (scaleFactor - minScale) /
                    (1 - minScale) * (1 - minAlpha)
            }
            else -> {
                // This page is way off-screen to the right.
                view.alpha = 0f
            }
        }
    }

    private companion object {
        const val MIN_SCALE = 0.85f
        const val MIN_ALPHA = 1f
    }
}
