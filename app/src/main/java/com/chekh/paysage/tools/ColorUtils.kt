package com.chekh.paysage.tools

const val HUE = 0
const val SATURATION = 1
const val LIGHTNESS = 2

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