package com.chekh.paysage.core.ui.fragment

import android.content.Intent
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chekh.paysage.core.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.tools.hideKeyboard

abstract class BaseFragment(@LayoutRes layoutId: Int = 0) : Fragment(layoutId) {

    private val backPressedHandler by lazyUnsafe {
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

    protected fun startActivityForResult(
        intent: Intent,
        listener: (resultCode: Int, data: Intent?) -> Unit
    ) {
        val requestCode = intent.hashCode()
        activityResultListeners.add(ActivityResultListener(requestCode, listener))
        startActivityForResult(intent, requestCode)
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
