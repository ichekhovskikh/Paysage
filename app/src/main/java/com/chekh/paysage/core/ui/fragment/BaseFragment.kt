package com.chekh.paysage.core.ui.fragment

import android.os.Bundle
import android.os.Parcelable
import android.view.WindowInsets
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chekh.paysage.core.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.core.ui.tools.hideKeyboard

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    private val backPressedHandler by lazy {
        ContainerBackPressedHandler(
            childFragmentManager,
            parentFragmentManager
        )
    }

    private var params: Any? = null

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

    @Suppress("UNCHECKED_CAST")
    fun <Params : Parcelable> getParams(): Params? =
        (params ?: arguments?.get(PARAMS_EXTRA_TAG)) as? Params

    fun <Params : Parcelable> setParams(params: Params) {
        val args = arguments ?: Bundle()
        args.putParcelable(PARAMS_EXTRA_TAG, params)
        arguments = args
    }

    open fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }

    private companion object {
        const val PARAMS_EXTRA_TAG = "paramsExtraTag"
    }
}
