package com.chekh.paysage.ui.handler

import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout.PanelState

class SlidingPanelBackPressedHandler(private val slidingPanel: SlidingUpPanelLayout) {

    fun onBackPressed(): Boolean {
        if (isSlidingPanelOpen(slidingPanel)) {
            slidingPanel.panelState = PanelState.COLLAPSED
            return true
        }
        return false
    }

    private fun isSlidingPanelOpen(slidingPanel: SlidingUpPanelLayout) =
        slidingPanel.panelState == PanelState.EXPANDED || slidingPanel.panelState == PanelState.ANCHORED
}