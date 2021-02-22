package com.chekh.paysage.core.ui.fragment

import android.content.Intent
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

    private val activityResultListeners = mutableSetOf<ActivityResultListener>()
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
    fun <Params : Parcelable> getParams(): Params {
        params = params ?: arguments?.getParcelable(EXTRA_PARAMS)
        return params as Params
    }

    fun <Params : Parcelable> setParams(params: Params) {
        val args = arguments ?: Bundle()
        arguments = args.apply { putParcelable(EXTRA_PARAMS, params) }
    }

    protected fun registerActivityResultListener(
        requestCode: Int,
        listener: (resultCode: Int, data: Intent?) -> Unit
    ) {
        activityResultListeners.add(ActivityResultListener(requestCode, listener))
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val listeners = activityResultListeners.filter { it.requestCode == requestCode }
        listeners.forEach { it.listener(resultCode, data) }
        activityResultListeners.removeAll(listeners)
    }

    open fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }

    private class ActivityResultListener(
        val requestCode: Int,
        val listener: (resultCode: Int, data: Intent?) -> Unit
    )

    private companion object {
        const val EXTRA_PARAMS = "EXTRA_PARAMS"
    }
}
