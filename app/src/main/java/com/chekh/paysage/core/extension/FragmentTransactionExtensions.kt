package com.chekh.paysage.core.extension

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

fun FragmentTransaction.addWithBackStack(@IdRes containerViewId: Int, fragment: Fragment) {
    add(containerViewId, fragment)
    addToBackStack(fragment::class.simpleName)
}
