package com.chekh.paysage.core.extension

import android.view.*
import androidx.core.view.marginBottom
import androidx.core.view.marginTop

fun View.setMarginTop(top: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.topMargin = top
    layoutParams = params
}

fun View.setMarginBottom(bottom: Int) {
    val params = layoutParams as? ViewGroup.MarginLayoutParams
    params?.bottomMargin = bottom
    layoutParams = params
}

fun View.setHeight(height: Int) {
    layoutParams = layoutParams.apply { this.height = height }
}

inline val View.absoluteHeight: Int
    get() = measuredHeight + marginTop + marginBottom

fun View.applyPadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}
