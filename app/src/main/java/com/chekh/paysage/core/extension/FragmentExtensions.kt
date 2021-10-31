package com.chekh.paysage.core.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE
import java.lang.IllegalArgumentException

fun Fragment.exit() = try {
    parentFragmentManager.popBackStack(this::class.simpleName, POP_BACK_STACK_INCLUSIVE)
} catch (ignored: IllegalStateException) {
}

inline fun <reified Args : Parcelable> Fragment.getArgs(): Args {
    val tag = Args::class.simpleName
    return arguments?.getParcelable<Args>(tag)
        ?: throw IllegalArgumentException("The required parameter is missing: $tag")
}

inline fun <reified Args : Parcelable> Fragment.setArgs(params: Args) {
    val tag = Args::class.simpleName
    val args = arguments ?: Bundle()
    arguments = args.apply { putParcelable(tag, params) }
}
