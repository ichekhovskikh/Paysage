package com.chekh.paysage.core.handler.backpressed

import androidx.fragment.app.FragmentManager
import com.chekh.paysage.core.ui.fragment.BaseFragment

class ContainerBackPressedHandler(
    private val childFragmentManager: FragmentManager,
    private val parentFragmentManager: FragmentManager? = null
) : BackPressedHandler {

    override fun onBackPressed(): Boolean {
        val fragments = childFragmentManager.fragments
        fragments.forEach { fragment ->
            if (fragment is BaseFragment && fragment.onBackPressed()) {
                return true
            }
        }
        if (parentFragmentManager != null && parentFragmentManager.backStackEntryCount > 0) {
            parentFragmentManager.popBackStack()
            return true
        }
        return false
    }
}
