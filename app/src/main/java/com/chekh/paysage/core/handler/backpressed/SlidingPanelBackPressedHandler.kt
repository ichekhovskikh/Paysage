package com.chekh.paysage.core.handler.backpressed

import androidx.fragment.app.FragmentManager
import com.chekh.slidinguppanel.SlidingUpPanelLayout
import com.chekh.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.chekh.slidinguppanel.isOpened

class SlidingPanelBackPressedHandler(
    private val slidingPanel: SlidingUpPanelLayout,
    fragmentManager: FragmentManager
) : BackPressedHandler {

    private val containerBackPressedHandler = ContainerBackPressedHandler(fragmentManager)

    override fun onBackPressed(): Boolean {
        return when {
            containerBackPressedHandler.onBackPressed() -> true
            slidingPanel.isOpened -> {
                slidingPanel.panelState = PanelState.COLLAPSED
                true
            }
            else -> false
        }
    }
}
