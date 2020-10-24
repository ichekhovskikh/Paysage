package com.chekh.paysage.core.extension

import android.view.View
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior

val <T : View> CustomBottomSheetBehavior<T>.isClosed
    get() = state in listOf(
        CustomBottomSheetBehavior.STATE_HIDDEN,
        CustomBottomSheetBehavior.STATE_COLLAPSED
    )

val <T : View> CustomBottomSheetBehavior<T>.isOpened
    get() = state in listOf(
        CustomBottomSheetBehavior.STATE_EXPANDED,
        CustomBottomSheetBehavior.STATE_HALF_EXPANDED
    )
