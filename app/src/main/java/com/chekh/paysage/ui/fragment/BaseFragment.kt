package com.chekh.paysage.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, null)
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