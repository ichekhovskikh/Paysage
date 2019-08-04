package com.chekh.paysage.model

import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette

enum class IconColor(@ColorInt val color: Int) {
    NOTHING(Color.TRANSPARENT),
    RED(Color.RED),
    YELLOW(Color.YELLOW),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE),
    PURPLE(Color.MAGENTA),
    BLACK(Color.BLACK);

    companion object {
        private const val HUE_INDEX = 0
        private const val SATURATION_INDEX = 1
        private const val LIGHTNESS_INDEX = 2

        fun get(@ColorInt color: Int) = values().find { it.color == color } ?: NOTHING

        fun getIconColor(icon: Bitmap): IconColor {
            val palette = Palette
                .from(icon)
                .clearFilters()
                .addFilter { _, hsl -> filter(hsl) }
                .generate()

            if (palette.swatches.isEmpty()) {
                return BLACK
            }
            val colorsPopulation = getColorsPopulation(palette)
            var iconColorType = NOTHING
            var prevIconColorType: IconColor = NOTHING
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
            return hsl[LIGHTNESS_INDEX] < maxLightness && hsl[LIGHTNESS_INDEX] > minLightness
        }

        private fun getColorsPopulation(palette: Palette): HashMap<IconColor, Int> {
            val colorsPopulation = hashMapOf<IconColor, Int>()
            palette.swatches.forEach { swatch ->
                val swatchColorType = getColorType(swatch.hsl)
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

        private fun isRed(hsl: FloatArray): Boolean {
            val colorRedHueStart = 320.0f
            val colorRedHueEnd = 28.0f
            return hsl[HUE_INDEX] <= colorRedHueEnd || hsl[HUE_INDEX] > colorRedHueStart
        }

        private fun isYellow(hsl: FloatArray): Boolean {
            val colorYellowHueStart = 28.0f
            val colorYellowHueEnd = 75.0f
            return hsl[HUE_INDEX] > colorYellowHueStart && hsl[HUE_INDEX] <= colorYellowHueEnd
        }

        private fun isGreen(hsl: FloatArray): Boolean {
            val colorGreenHueStart = 75.0f
            val colorGreenHueEnd = 180.0f
            return hsl[HUE_INDEX] > colorGreenHueStart && hsl[HUE_INDEX] <= colorGreenHueEnd
        }

        private fun isBlue(hsl: FloatArray): Boolean {
            val colorsBlueHueStart = 180.0f
            val colorsBlueHueEnd = 250.0f
            return hsl[HUE_INDEX] > colorsBlueHueStart && hsl[HUE_INDEX] <= colorsBlueHueEnd
        }

        private fun isPurple(hsl: FloatArray): Boolean {
            val colorsPurpleHueStart = 250.0f
            val colorsPurpleHueEnd = 320.0f
            return hsl[HUE_INDEX] > colorsPurpleHueStart && hsl[HUE_INDEX] <= colorsPurpleHueEnd
        }

        private fun isBlack(hsl: FloatArray): Boolean {
            val colorBlackSaturationEnd = 0.25f
            val colorBlackLightnessEnd = 0.2f
            return hsl[LIGHTNESS_INDEX] < colorBlackLightnessEnd || hsl[SATURATION_INDEX] < colorBlackSaturationEnd
        }

        private fun isWhite(hsl: FloatArray): Boolean {
            val colorWhiteLightnessStart = 0.8f
            return hsl[LIGHTNESS_INDEX] > colorWhiteLightnessStart
        }
    }
}