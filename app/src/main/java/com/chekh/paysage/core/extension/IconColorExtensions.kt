package com.chekh.paysage.core.extension

import android.graphics.Bitmap
import androidx.palette.graphics.Palette
import com.chekh.paysage.common.data.model.IconColor

private const val HUE = 0
private const val SATURATION = 1
private const val LIGHTNESS = 2

fun Int.toIconColor() = IconColor.values().find { it.color == this } ?: IconColor.NOTHING

fun Bitmap?.toIconColor(): IconColor {
    if (this == null) return IconColor.NOTHING
    val palette = Palette
        .from(this)
        .clearFilters()
        .addFilter { _, hsl -> filter(hsl) }
        .generate()

    if (palette.swatches.isEmpty()) {
        return IconColor.BLACK
    }
    val colorsPopulation = getColorsPopulation(palette)
    var iconColorType = IconColor.NOTHING
    var prevIconColorType: IconColor = IconColor.NOTHING
    var maxPopulation = 0
    var prevMaxPopulation = 0
    for ((colorType, population) in colorsPopulation) {
        if (colorType != IconColor.NOTHING) {
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
    if (iconColorType == IconColor.BLACK && prevMaxPopulation * 3 > maxPopulation) {
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
        isWhite(hsl) -> IconColor.NOTHING
        isBlack(hsl) -> IconColor.BLACK
        isRed(hsl) -> IconColor.RED
        isYellow(hsl) -> IconColor.YELLOW
        isGreen(hsl) -> IconColor.GREEN
        isBlue(hsl) -> IconColor.BLUE
        isPurple(hsl) -> IconColor.PURPLE
        else -> IconColor.NOTHING
    }
}

fun isRed(hsl: FloatArray): Boolean {
    val colorRedHueStart = 320.0f
    val colorRedHueEnd = 28.0f
    return hsl[HUE] <= colorRedHueEnd || hsl[HUE] > colorRedHueStart
}

fun isYellow(hsl: FloatArray): Boolean {
    val colorYellowHueStart = 28.0f
    val colorYellowHueEnd = 75.0f
    return hsl[HUE] > colorYellowHueStart && hsl[HUE] <= colorYellowHueEnd
}

fun isGreen(hsl: FloatArray): Boolean {
    val colorGreenHueStart = 75.0f
    val colorGreenHueEnd = 180.0f
    return hsl[HUE] > colorGreenHueStart && hsl[HUE] <= colorGreenHueEnd
}

fun isBlue(hsl: FloatArray): Boolean {
    val colorsBlueHueStart = 180.0f
    val colorsBlueHueEnd = 250.0f
    return hsl[HUE] > colorsBlueHueStart && hsl[HUE] <= colorsBlueHueEnd
}

fun isPurple(hsl: FloatArray): Boolean {
    val colorsPurpleHueStart = 250.0f
    val colorsPurpleHueEnd = 320.0f
    return hsl[HUE] > colorsPurpleHueStart && hsl[HUE] <= colorsPurpleHueEnd
}

fun isBlack(hsl: FloatArray): Boolean {
    val colorBlackSaturationEnd = 0.25f
    val colorBlackLightnessEnd = 0.2f
    return hsl[LIGHTNESS] < colorBlackLightnessEnd || hsl[SATURATION] < colorBlackSaturationEnd
}

fun isWhite(hsl: FloatArray): Boolean {
    val colorWhiteLightnessStart = 0.8f
    return hsl[LIGHTNESS] > colorWhiteLightnessStart
}
