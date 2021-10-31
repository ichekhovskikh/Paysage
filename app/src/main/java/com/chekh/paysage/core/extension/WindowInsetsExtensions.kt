package com.chekh.paysage.core.extension

import android.app.Activity
import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat.Type
import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.fragment.app.Fragment

inline val Insets.statusBar: Int get() = top

inline val Insets.navigationBar: Int get() = bottom

fun View.applyWindowInsets(
    @InsetsType typeMask: Int = Type.statusBars() or Type.navigationBars(),
    onApply: (insets: Insets) -> Unit
) {
    var lastWindowInsets: Insets? = null
    ViewCompat.setOnApplyWindowInsetsListener(this) { _, insets ->
        if (isAttachedToWindow) {
            val screenInsets = insets.getInsets(typeMask)
            if (screenInsets != lastWindowInsets) {
                lastWindowInsets = screenInsets
                onApply(screenInsets)
            }
        } else {
            setOnApplyWindowInsetsListener(null)
        }
        insets
    }
}

fun Activity.applyWindowInsets(
    @InsetsType typeMask: Int = Type.statusBars() or Type.navigationBars(),
    onApply: (insets: Insets) -> Unit
) {
    contentView?.applyWindowInsets(typeMask, onApply)
}

fun Fragment.applyWindowInsets(
    @InsetsType typeMask: Int = Type.statusBars() or Type.navigationBars(),
    onApply: (insets: Insets) -> Unit
) {
    view?.applyWindowInsets(typeMask, onApply)
}
