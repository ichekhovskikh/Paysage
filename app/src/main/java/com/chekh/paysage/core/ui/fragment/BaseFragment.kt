package com.chekh.paysage.core.ui.fragment

import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chekh.paysage.core.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.core.ui.tools.hideKeyboard

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private val backPressedHandler by lazy { ContainerBackPressedHandler(childFragmentManager) }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }

    @CallSuper
    open fun onApplyWindowInsets(insets: WindowInsets) {
        val fragments = childFragmentManager.fragments
        fragments.forEach { fragment ->
            if (fragment is BaseFragment) {
                fragment.onApplyWindowInsets(insets)
            }
        }
    }

    open fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }
}
