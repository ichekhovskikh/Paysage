package com.chekh.paysage.feature.home.data.model

import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette
import com.chekh.paysage.tools.*

enum class IconColor(@ColorInt val color: Int) {
    NOTHING(Color.TRANSPARENT),
    RED(Color.RED),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    PURPLE(Color.MAGENTA),
    BLACK(Color.BLACK);

    companion object {

        fun get(@ColorInt color: Int) = values().find { it.color == color } ?: NOTHING

        fun get(icon: Bitmap?): IconColor {
            if (icon == null) return NOTHING
            val palette = Palette
                .from(icon)
                .clearFilters()
                .addFilter { _, hsl ->
                    filter(
                        hsl
                    )
                }
                .generate()

            if (palette.swatches.isEmpty()) {
                return BLACK
            }
            val colorsPopulation =
                getColorsPopulation(
                    palette
                )
            var iconColorType =
                NOTHING
            var prevIconColorType: IconColor =
                NOTHING
            var maxPopulation = 0
            var prevMaxPopulation = 0
            for ((colorType, population) in colorsPopulation) {
                if (colorType != NOTHING) {
                    if (population >= maxPopulation) {
                        prevIconColorType = iconColorType
                        prevMaxPopulation = maxPopulation
                        maxPopulation = population
                        iconColorType = colorType
                    } else if (population >= prevMaxPopulation) {
                        prevIconColorType = colorType
                        prevMaxPopulation = population
                    }
                }
            }
            if (iconColorType == BLACK && prevMaxPopulation * 3 > maxPopulation) {
                iconColorType = prevIconColorType
            }
            return iconColorType
        }

        private fun filter(hsl: FloatArray): Boolean {
            val maxLightness = 0.95f
            val minLightness = 0.05f
            return hsl[LIGHTNESS] < maxLightness && hsl[LIGHTNESS] > minLightness
        }

        private fun getColorsPopulation(palette: Palette): HashMap<IconColor, Int> {
            val colorsPopulation = hashMapOf<IconColor, Int>()
            palette.swatches.forEach { swatch ->
                val swatchColorType =
                    getColorType(
                        swatch.hsl
                    )
                if (colorsPopulation.containsKey(swatchColorType)) {
                    colorsPopulation[swatchColorType]?.let {
                        colorsPopulation[swatchColorType] = it + swatch.population
                    }
                } else colorsPopulation[swatchColorType] = swatch.population
            }
            return colorsPopulation
        }

        private fun getColorType(hsl: FloatArray): IconColor {
            return when {
                isWhite(hsl) -> NOTHING
                isBlack(hsl) -> BLACK
                isRed(hsl) -> RED
                isYellow(hsl) -> YELLOW
                isGreen(hsl) -> GREEN
                isBlue(hsl) -> BLUE
                isPurple(hsl) -> PURPLE
                else -> NOTHING
            }
        }
    }
}