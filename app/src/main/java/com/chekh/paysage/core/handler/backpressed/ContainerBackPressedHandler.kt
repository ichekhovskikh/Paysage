package com.chekh.paysage.core.handler.backpressed

import androidx.fragment.app.FragmentManager
import com.chekh.paysage.core.ui.fragment.BaseFragment

open class ContainerBackPressedHandler(
    private val childFragmentManager: FragmentManager,
    private val parentFragmentManager: FragmentManager? = null
) : BackPressedHandler {

    override fun onBackPressed(): Boolean {
        val fragments = childFragmentManager.fragments
        for (index in fragments.size - 1 downTo 0) {
            val fragment = fragments[index]
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
