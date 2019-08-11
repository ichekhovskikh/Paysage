package com.chekh.paysage.ui.handler

import androidx.fragment.app.FragmentManager
import com.chekh.paysage.ui.fragment.core.BaseFragment

class ContainerBackPressedHandler(private val fragmentManager: FragmentManager) {

    fun onBackPressed(): Boolean {
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