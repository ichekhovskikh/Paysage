package com.chekh.paysage.common.data.model

import android.graphics.Color
import androidx.annotation.ColorInt

enum class IconColor(@ColorInt val color: Int) {
    NOTHING(Color.TRANSPARENT),
    RED(Color.RED),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    PURPLE(Color.MAGENTA),
    BLACK(Color.BLACK)
}
