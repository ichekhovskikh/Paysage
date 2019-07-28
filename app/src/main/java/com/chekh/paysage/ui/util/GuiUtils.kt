package com.chekh.paysage.ui.util

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chekh.paysage.PaysageApp.Companion.launcher
import android.app.Activity
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import android.view.*
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

private val statusBarDecorator: StatusBarDecorator = CommonStatusBarDecorator()

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, callback: (data: T) -> Unit) {
    observe(owner, Observer<T> { data ->
        data?.let { callback.invoke(it) }
    })
}

fun Activity.statusDarkBarMode(isDark: Boolean): Boolean = statusBarDecorator.statusBarDarkMode(this, isDark)

fun Activity.setTransparentStatusBar() = statusBarDecorator.setTransparentStatusBar(this)

fun View.setMarginTop(top: Int) {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    params.topMargin = top
}

fun View.getMarginTop(): Int {
    val params = layoutParams as ViewGroup.MarginLayoutParams
    return params.topMargin
}

fun View.applyPadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun convertDpToPx(dp: Float): Float {
    val metrics = launcher.resources.displayMetrics
    return dp * metrics.density
}

fun convertPxToDp(px: Float): Float {
    val metrics = launcher.resources.displayMetrics
    return px / metrics.density
}