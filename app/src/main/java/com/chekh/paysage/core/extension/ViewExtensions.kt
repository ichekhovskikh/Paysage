package com.chekh.paysage.core.extension

import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorRes
import androidx.annotation.IntRange
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.viewpager2.widget.ViewPager2

inline var View.topMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.topMargin ?: 0
    set(value) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        if (params.topMargin == value) return
        params.topMargin = value
        layoutParams = params
    }

inline var View.bottomMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.bottomMargin ?: 0
    set(value) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        if (params.bottomMargin == value) return
        params.bottomMargin = value
        layoutParams = params
    }

inline var View.startMargin: Int
    get() = (layoutParams as? ViewGroup.MarginLayoutParams)?.leftMargin ?: 0
    set(value) {
        val params = layoutParams as? ViewGroup.MarginLayoutParams ?: return
        if (params.leftMargin == value) return
        params.leftMargin = value
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

fun FrameLayout.setForegroundColorResource(
    @ColorRes colorRes: Int,
    @IntRange(from = 0, to = 255) alpha: Int
) {
    foreground = ColorDrawable(ResourcesCompat.getColor(resources, colorRes, null))
        .apply { this.alpha = alpha }
}

fun View.setHierarchyViewPagerTouching(isUserInputEnabled: Boolean) {
    var parent: View? = this
    while (parent != null) {
        if (parent is ViewPager2) {
            parent.isUserInputEnabled = isUserInputEnabled
            return
        }
        parent = parent.parent as? View ?: return
    }
}
