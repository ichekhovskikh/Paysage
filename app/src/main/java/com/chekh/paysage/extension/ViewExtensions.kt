package com.chekh.paysage.extension

import android.view.View
import android.view.ViewGroup
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction?) {
    beginTransaction().func()?.commit()
}

fun View.setMarginTop(top: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin = top
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