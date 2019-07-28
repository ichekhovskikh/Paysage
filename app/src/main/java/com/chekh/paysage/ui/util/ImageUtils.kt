package com.chekh.paysage.ui.util

import android.graphics.*
import android.graphics.Color.argb
import android.graphics.drawable.BitmapDrawable
import android.util.Base64
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import com.chekh.paysage.PaysageApp
import java.io.ByteArrayOutputStream

private const val LINE_THICKNESS = 15f

fun Bitmap.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun toBitmap(base64: String): Bitmap? {
    return try {
        val encodeByte = Base64.decode(base64, Base64.DEFAULT)
        BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
    } catch (e: Exception) {
        null
    }
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
        color = PaysageApp.launcher.resources.getColor(colorRes)
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

@ColorInt
fun createColorAlpha(@ColorInt color: Int, alpha: Float): Int {
    return argb((Color.alpha(color).toFloat() * alpha).toInt(), Color.red(color), Color.green(color), Color.blue(color))
}