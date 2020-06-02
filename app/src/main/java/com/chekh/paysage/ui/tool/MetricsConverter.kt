package com.chekh.paysage.ui.tool

import android.content.Context

class MetricsConverter(private val context: Context) {

    fun dpToPx(dp: Float): Int {
        val metrics = context.resources.displayMetrics
        return (dp * metrics.density).toInt()
    }

    fun pxToDp(px: Float): Float {
        val metrics = context.resources.displayMetrics
        return px / metrics.density
    }
}