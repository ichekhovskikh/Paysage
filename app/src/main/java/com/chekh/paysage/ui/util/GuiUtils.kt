package com.chekh.paysage.ui.util

import androidx.annotation.ColorRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chekh.paysage.PaysageApp.Companion.instance
import android.graphics.drawable.BitmapDrawable
import android.graphics.*
import android.app.Activity
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import android.view.*

private const val LINE_THICKNESS = 15f
private val statusBarDecorator: StatusBarDecorator = CommonStatusBarDecorator()

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun Activity.statusDarkBarMode(isDark: Boolean): Boolean = statusBarDecorator.statusBarDarkMode(this, isDark)

fun Activity.setTransparentStatusBar() = statusBarDecorator.setTransparentStatusBar(this)

fun addStatusBarMarginTop(view: View) {
    view.setOnApplyWindowInsetsListener { _, insets ->
        view.addMarginTop(insets.systemWindowInsetTop)
        insets
    }
}

fun addNavigationBarPaddingBottom(view: View) {
    view.setOnApplyWindowInsetsListener { _, insets ->
        val height = insets.systemWindowInsetBottom
        view.setPadding(view.paddingLeft, view.paddingTop, view.paddingRight, view.paddingBottom + height)
        insets
    }
}

fun addNavigationBarMarginBottom(view: View) {
    view.setOnApplyWindowInsetsListener { _, insets ->
        val height = insets.systemWindowInsetBottom
        val params = view.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin += height
        insets
    }
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
    return convertDpToPx(dp) / metrics.heightPixels
}

fun convertDpToPx(dp: Float): Float {
    val metrics = instance.resources.displayMetrics
    return dp * metrics.density
}

fun convertPxToDp(px: Float): Float {
    val metrics = instance.resources.displayMetrics
    return px / metrics.density
}

fun View.makeBitmapDrawableScreenshot(): BitmapDrawable {
    val bitmap = makeBitmapScreenshot()
    val drawable = BitmapDrawable(resources, bitmap)
    drawable.bounds = Rect(left, top, left + width, top + height)
    return drawable
}

fun View.makeBitmapBorder(@ColorRes colorRes: Int): Bitmap {
    val bitmap = makeBitmapScreenshot()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = LINE_THICKNESS
        color = instance.resources.getColor(colorRes)
    }
    val canvas = Canvas(bitmap)
    canvas.drawBitmap(bitmap, 0f, 0f, null)
    canvas.drawRect(rect, paint)
    return bitmap
}

fun View.makeBitmapScreenshot(): Bitmap {
    return Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888).also {
        draw(Canvas(it))
    }
}