package com.chekh.paysage.ui.view.stickyheader

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

class StickyRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val scrollListener = OnScrollHeaderListener()

    init {
        addOnScrollListener(scrollListener)
    }

    override fun smoothScrollToPosition(position: Int) {
        val view = layoutManager?.findViewByPosition(position) ?: return
        if (view.top < 0) {
            super.smoothScrollToPosition(position)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        invalidateScroll()
    }

    private fun invalidateScroll() {
        scrollListener.onScrolled(this, 0, 0)
    }
}