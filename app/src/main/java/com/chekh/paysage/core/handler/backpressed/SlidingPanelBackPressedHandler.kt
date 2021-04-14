package com.chekh.paysage.core.handler.backpressed

import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.core.extension.isOpened
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior

class SlidingPanelBackPressedHandler(
    private val bottomSheetBehavior: CustomBottomSheetBehavior<View>?,
    private val scrollableView: RecyclerView?,
    fragmentManager: FragmentManager
) : BackPressedHandler {

    private val containerBackPressedHandler = ContainerBackPressedHandler(fragmentManager)

    override fun onBackPressed() = when {
        containerBackPressedHandler.onBackPressed() -> true
        bottomSheetBehavior?.isOpened == true -> {
            scrollableView?.scrollToPosition(0)
            bottomSheetBehavior.state = CustomBottomSheetBehavior.STATE_COLLAPSED
            true
        }
        else -> false
    }
}
