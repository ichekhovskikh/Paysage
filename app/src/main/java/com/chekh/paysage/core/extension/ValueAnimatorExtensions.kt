package com.chekh.paysage.core.extension

import android.animation.ValueAnimator

fun ValueAnimator.reStart() {
    cancel()
    start()
}

fun ValueAnimator.reReverse() {
    cancel()
    reverse()
}
