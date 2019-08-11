package com.chekh.paysage.ui.handler

import androidx.fragment.app.FragmentManager
import com.chekh.paysage.ui.view.core.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.ui.view.core.slidingpanel.SlidingUpPanelLayout.PanelState

class SlidingPanelBackPressedHandler(private val slidingPanel: SlidingUpPanelLayout, fragmentManager: FragmentManager) {

    private val containerBackPressedHandler = ContainerBackPressedHandler(fragmentManager)

    fun onBackPressed(): Boolean {
        return if (containerBackPressedHandler.onBackPressed()) {
            true
        } else if (isSlidingPanelOpen(slidingPanel)) {
            slidingPanel.panelState = PanelState.COLLAPSED
            true
        } else {
            false
        }
    }

    private fun isSlidingPanelOpen(slidingPanel: SlidingUpPanelLayout) =
        slidingPanel.panelState == PanelState.EXPANDED || slidingPanel.panelState == PanelState.ANCHORED
}