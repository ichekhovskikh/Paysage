package com.chekh.paysage.core.ui.fragment

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.chekh.paysage.core.handler.backpressed.ContainerBackPressedHandler
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.tools.hideKeyboard

abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {

    constructor() : this(layoutId = 0)

    private val backPressedHandler by lazyUnsafe {
        ContainerBackPressedHandler(
            childFragmentManager,
            parentFragmentManager
        )
    }

    override fun onStop() {
        super.onStop()
        view?.hideKeyboard()
    }

    open fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }
}
