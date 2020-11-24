package com.chekh.paysage.core.extension

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE

fun Fragment.exit() {
    parentFragmentManager.popBackStack(this::class.simpleName, POP_BACK_STACK_INCLUSIVE)
}
