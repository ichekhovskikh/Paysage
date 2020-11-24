package com.chekh.paysage.core.ui.tools

data class Size(val width: Float, val height: Float) {

    companion object {
        val ZERO = Size(0f, 0f)
    }
}

infix fun Float.on(height: Float) = Size(this, height)

infix fun Int.on(height: Int) = this.toFloat() on height.toFloat()
