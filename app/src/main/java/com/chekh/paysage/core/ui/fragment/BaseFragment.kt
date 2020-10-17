package com.chekh.paysage.core.ui.fragment

import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import com.chekh.paysage.core.ui.tools.hideKeyboard
import dagger.android.support.DaggerFragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : DaggerFragment(layoutId) {

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }

    open fun onBackPressed(): Boolean {
        return false
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
}
