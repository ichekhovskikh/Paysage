package com.chekh.paysage.ui

import android.content.Context
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chekh.paysage.PaysageApp.Companion.instance

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun addStatusBarMarginTop(view: View) {
    val params = view.layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin += getStatusBarHeight(instance)
}

fun View.addMarginTop(top: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin += top
    layoutParams = params
}

fun View.getMarginTop(): Int {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    return params.topMargin
}

fun convertHeightDpToPercentage(dp: Float): Float {
    val metrics = instance.resources.displayMetrics
    return  convertDpToPx(dp) / metrics.heightPixels.toFloat()
}

fun convertDpToPx(dp: Float): Float {
    return dp * instance.resources.displayMetrics.density
}