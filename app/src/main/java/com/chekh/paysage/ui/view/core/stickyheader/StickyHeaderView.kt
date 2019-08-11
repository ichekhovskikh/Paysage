package com.chekh.paysage.ui.view.core.stickyheader

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

abstract class StickyHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {
    abstract fun delegateEvents(view: View)
    abstract fun copyState(view: View)
}
