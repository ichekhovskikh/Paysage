package com.chekh.paysage.ui.fragment

import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import dagger.android.support.DaggerFragment

abstract class BaseFragment(@LayoutRes layoutId: Int) : DaggerFragment(layoutId) {

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