package com.chekh.paysage.core.ui.fragment

import android.content.Intent
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

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
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
}
