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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

private const val LINE_THICKNESS = 15f
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