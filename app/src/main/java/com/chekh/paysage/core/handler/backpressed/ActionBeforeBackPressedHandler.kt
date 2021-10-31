package com.chekh.paysage.core.handler.backpressed

import androidx.fragment.app.Fragment

class ActionBeforeBackPressedHandler(
    private val fragment: Fragment,
    private val actionBefore: () -> Unit
) : BackPressedHandler {

    override fun onBackPressed(): Boolean {
        val lastFragment = fragment.parentFragmentManager.fragments.lastOrNull()
        if (fragment === lastFragment) {
            actionBefore()
            return true
        }
        return false
    }
}
