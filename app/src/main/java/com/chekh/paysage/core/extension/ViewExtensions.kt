package com.chekh.paysage.core.extension

import android.graphics.Rect
import android.view.*
import androidx.core.view.marginBottom
import androidx.core.view.marginTop

inline var View.topMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0
    set(value) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams
        if (params?.topMargin == value) return
        params?.topMargin = value
        layoutParams = params
    }

inline var View.bottomMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
    set(value) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams
        if (params?.bottomMargin == value) return
        params?.bottomMargin = value
        layoutParams = params
    }

inline var View.startMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0
    set(value) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams
        if (params?.leftMargin == value) return
        params?.leftMargin = value
        layoutParams = params
    }

inline var View.layoutHeight: Int
    get() = layoutParams.height
    set(value) {
        layoutParams = layoutParams.apply { this.height = value }
    }

inline val View.absoluteHeight: Int
    get() = measuredHeight + marginTop + marginBottom

inline val View.availableHeight: Int
    get() = measuredHeight - paddingBottom - paddingTop

inline val View.availableWidth: Int
    get() = measuredWidth - paddingStart - paddingEnd

inline val View.bounds: Rect
    get() = Rect(
        x.toInt() + paddingStart,
        y.toInt() + paddingTop,
        x.toInt() + measuredWidth - paddingEnd,
        y.toInt() + measuredHeight - paddingBottom
    )

fun View.applyPadding(
    left: Int = paddingLeft,
    top: Int = paddingTop,
    right: Int = paddingRight,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}
