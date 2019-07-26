package com.chekh.paysage.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.chekh.paysage.PaysageApp.Companion.instance
import android.graphics.drawable.BitmapDrawable

private const val LINE_THICKNESS = 15f

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