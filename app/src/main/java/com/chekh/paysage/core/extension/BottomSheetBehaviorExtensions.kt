package com.chekh.paysage.core.extension

import android.view.View
import com.google.android.material.bottomsheet.BottomSheetBehavior

const val BOTTOM_SHEET_HALF_RATIO_INACCURACY = 0.025f

interface BottomSheetListener {
    fun onStateChanged(bottomSheet: View, @BottomSheetBehavior.State newState: Int) {}
    fun onSlide(bottomSheet: View, slideOffset: Float) {}
}

fun <T : View> BottomSheetBehavior<T>.addBottomSheetListener(listener: BottomSheetListener) {
    addBottomSheetCallback(
        object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                listener.onStateChanged(bottomSheet, newState)
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                listener.onSlide(bottomSheet, slideOffset)
            }

        }
    )
}

val <T : View> BottomSheetBehavior<T>.isClosed
    get() = state in listOf(
        BottomSheetBehavior.STATE_HIDDEN,
        BottomSheetBehavior.STATE_COLLAPSED
    )

val <T : View> BottomSheetBehavior<T>.isOpened
    get() = state in listOf(
        BottomSheetBehavior.STATE_EXPANDED,
        BottomSheetBehavior.STATE_HALF_EXPANDED
    )
