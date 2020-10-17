package com.chekh.paysage.core.handler.backpressed

import androidx.fragment.app.FragmentManager
import com.chekh.paysage.core.ui.fragment.BaseFragment

class ContainerBackPressedHandler(
    private val fragmentManager: FragmentManager
) : BackPressedHandler {

    override fun onBackPressed(): Boolean {
        val fragments = fragmentManager.fragments
        var handled = false
        for (fragment in fragments) {
            if (fragment is BaseFragment) {
                handled = fragment.onBackPressed()
                if (handled) break
            }
        }
        return handled
    }
}
