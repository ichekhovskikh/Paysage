package com.chekh.paysage.ui.view.smoothscroll

import android.content.Context
import android.util.AttributeSet

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SmoothScrollLinearLayoutManager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : LinearLayoutManager(context, attrs, defStyleAttr, defStyleRes) {

    private val smoothScroller = FastSmoothScroller(context, this)

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView,
        state: RecyclerView.State?,
        position: Int
    ) {
        smoothScroller.targetPosition = position
        startSmoothScroll(smoothScroller)
    }
}