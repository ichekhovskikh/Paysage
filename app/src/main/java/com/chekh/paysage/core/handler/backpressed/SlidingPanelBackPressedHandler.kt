package com.chekh.paysage.core.handler.backpressed

import androidx.fragment.app.FragmentManager
import com.chekh.paysage.core.extension.isOpened
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior

class SlidingPanelBackPressedHandler(
    private val bottomSheetBehavior: CustomBottomSheetBehavior<*>,
    fragmentManager: FragmentManager
) : BackPressedHandler {

    private val containerBackPressedHandler = ContainerBackPressedHandler(fragmentManager)

    override fun onBackPressed(): Boolean {
        return when {
            containerBackPressedHandler.onBackPressed() -> true
            bottomSheetBehavior.isOpened -> {
                bottomSheetBehavior.state = CustomBottomSheetBehavior.STATE_COLLAPSED
                true
            }
            else -> false
        }
    }
}
