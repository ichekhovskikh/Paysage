package com.chekh.paysage.core.ui.tools

import android.content.Context
import android.graphics.*
import android.graphics.Color.argb
import android.graphics.drawable.BitmapDrawable
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Base64
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream

private const val LINE_THICKNESS = 15f

fun Bitmap.toBase64(): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
    val byteArray = byteArrayOutputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}

fun createBitmap(base64: String): Bitmap {
    val encodeByte = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
}

fun View.makeBitmapDrawableScreenshot(): BitmapDrawable =
    BitmapDrawable(resources, makeBitmapScreenshot()).also {
        it.bounds = Rect(left, top, left + width, top + height)
    }

fun View.setBackgroundBitmap(bitmap: Bitmap) {
    background = BitmapDrawable(resources, bitmap).also {
        it.bounds = Rect(left, top, left + width, top + height)
    }
}

fun List<Bitmap>.combine(): Bitmap {
    val fistBitmap = firstOrNull() ?: throw IllegalArgumentException("Required bitmap")
    return fistBitmap.copy(fistBitmap.config, true).also { comboBitmap ->
        val canvas = Canvas(comboBitmap)
        for (index in 1 until size) {
            canvas.drawBitmap(this[index], 0f, 0f, null)
        }
    }
}

fun View.makeBitmapBorder(@ColorRes colorRes: Int): Bitmap {
    val bitmap = makeBitmapScreenshot()
    val rect = Rect(0, 0, bitmap.width, bitmap.height)
    val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = LINE_THICKNESS
        color = ContextCompat.getColor(context, colorRes)
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

fun View.makeBitmapScreenshot(scaleFactor: Float): Bitmap {
    val bitmap = Bitmap.createBitmap(
        (measuredWidth * scaleFactor).toInt(),
        (measuredHeight * scaleFactor).toInt(),
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    val matrix = Matrix()
    matrix.preScale(scaleFactor, scaleFactor)
    canvas.setMatrix(matrix)
    draw(canvas)
    return bitmap
}

fun Bitmap.blur(context: Context, scale: Float, radius: Float): Bitmap {
    val blurBitmap = Bitmap.createScaledBitmap(
        this,
        (width / scale).toInt(),
        (height / scale).toInt(),
        false
    )
    val renderScript = RenderScript.create(context)
    val input = Allocation.createFromBitmap(renderScript, blurBitmap)
    val output = Allocation.createTyped(renderScript, input.type)
    val script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))
    script.setRadius(radius)
    script.setInput(input)
    script.forEach(output)
    output.copyTo(blurBitmap)
    return blurBitmap
}

fun Bitmap?.isSame(other: Bitmap?): Boolean {
    if (this == other) return true
    if (this == null && other == null) return true
    if (this == null || other == null) return false
    return sameAs(other)
}

@ColorInt
fun createColorAlpha(@ColorInt color: Int, alpha: Float): Int {
    return argb(
        (Color.alpha(color).toFloat() * alpha).toInt(),
        Color.red(color),
        Color.green(color),
        Color.blue(color)
    )
}

fun paint(style: Paint.Style, @ColorInt color: Int, strokeWidth: Float) = Paint().apply {
    this.style = style
    this.color = color
    this.strokeWidth = strokeWidth
}

fun alphaColor(color: Int, alpha: Float): Int =
    argb((255 * alpha).toInt(), Color.red(color), Color.green(color), Color.blue(color))
