package com.chekh.paysage.ui.view.smoothscroll

import android.content.Context

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SmoothScrollLinearLayoutManager @JvmOverloads constructor(
    context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private val smoothScroller =
        TopSmoothScroller(context, this)

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }
}