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

    fun pxToPercentage(px: Int, axis: Int = AXIS_Y): Float {
        val metrics = context.resources.displayMetrics
        val height = when(axis) {
            AXIS_X -> metrics.widthPixels
            else -> metrics.heightPixels
        }
        return px.toFloat() / height
    }

    companion object {
        const val AXIS_X = 0
        const val AXIS_Y = 1
    }
}