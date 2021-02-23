package com.chekh.paysage.core.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import java.lang.IllegalArgumentException

fun Fragment.exit() {
    parentFragmentManager.popBackStack(this::class.simpleName, POP_BACK_STACK_INCLUSIVE)
}

inline fun <reified Params : Parcelable> Fragment.getParams(): Params {
    val tag = Params::class.simpleName
    return arguments?.getParcelable<Params>(tag)
        ?: throw IllegalArgumentException("The required parameter is missing: $tag")
}

inline fun <reified Params : Parcelable> Fragment.setParams(params: Params) {
    val tag = Params::class.simpleName
    val args = arguments ?: Bundle()
    arguments = args.apply { putParcelable(tag, params) }
}
