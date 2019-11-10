package com.chekh.paysage.ui.util

import com.chekh.paysage.PaysageApp.Companion.launcher

object MetricsConvector {

    fun convertDpToPx(dp: Float): Int {
        val metrics = launcher.resources.displayMetrics
        return (dp * metrics.density).toInt()
    }

    fun convertPxToDp(px: Float): Float {
        val metrics = launcher.resources.displayMetrics
        return px / metrics.density
    }
}