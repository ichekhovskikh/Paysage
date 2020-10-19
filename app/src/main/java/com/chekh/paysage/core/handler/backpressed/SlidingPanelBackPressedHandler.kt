package com.chekh.paysage.core.handler.backpressed

import androidx.fragment.app.FragmentManager
import com.chekh.paysage.core.extension.isOpened
import com.google.android.material.bottomsheet.BottomSheetBehavior

class SlidingPanelBackPressedHandler(
    private val bottomSheetBehavior: BottomSheetBehavior<*>,
    fragmentManager: FragmentManager
) : BackPressedHandler {

    private val containerBackPressedHandler = ContainerBackPressedHandler(fragmentManager)

    override fun onBackPressed(): Boolean {
        return when {
            containerBackPressedHandler.onBackPressed() -> true
            bottomSheetBehavior.isOpened -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                true
            }
            else -> false
        }
    }
}
