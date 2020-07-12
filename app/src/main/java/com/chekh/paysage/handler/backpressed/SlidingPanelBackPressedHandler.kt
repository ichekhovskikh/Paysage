package com.chekh.paysage.handler.backpressed

import androidx.fragment.app.FragmentManager
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState

class SlidingPanelBackPressedHandler(
    private val slidingPanel: SlidingUpPanelLayout,
    fragmentManager: FragmentManager
) : BackPressedHandler {

    private val containerBackPressedHandler = ContainerBackPressedHandler(fragmentManager)

    override fun onBackPressed(): Boolean {
        return when {
            containerBackPressedHandler.onBackPressed() -> true
            isSlidingPanelOpen(slidingPanel) -> {
                slidingPanel.panelState = PanelState.COLLAPSED
                true
            }
            else -> false
        }
    }

    private fun isSlidingPanelOpen(slidingPanel: SlidingUpPanelLayout) =
        slidingPanel.panelState == PanelState.EXPANDED || slidingPanel.panelState == PanelState.ANCHORED
}