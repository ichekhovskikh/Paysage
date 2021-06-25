package com.chekh.paysage.core.extension

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette

inline val WallpaperManager.fastColor: Int
    @SuppressLint("MissingPermission")
    get() = Palette
        .from(fastDrawable.toBitmap())
        .generate()
        .vibrantSwatch
        ?.rgb
        ?: Color.BLACK

inline fun WallpaperManager.onWallpaperChangedCompat(crossinline listener: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
        addOnColorsChangedListener(
            { _, _ -> listener.invoke() },
            Handler(Looper.getMainLooper())
        )
    }
}
