package com.chekh.paysage.core.extension

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Color
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
