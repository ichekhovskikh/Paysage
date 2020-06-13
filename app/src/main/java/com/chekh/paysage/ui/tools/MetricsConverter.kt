package com.chekh.paysage.ui.tools

import android.content.Context
import javax.inject.Inject

class MetricsConverter @Inject constructor(private val context: Context) {

    fun dpToPx(dp: Float): Int {
        val metrics = context.resources.displayMetrics
        return (dp * metrics.density).toInt()
    }

    fun pxToDp(px: Float): Float {
        val metrics = context.resources.displayMetrics
        return px / metrics.density
    }
}