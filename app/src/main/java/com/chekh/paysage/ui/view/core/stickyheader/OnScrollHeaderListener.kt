package com.chekh.paysage.ui.view.core.stickyheader

import android.widget.AbsListView
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.extension.absoluteHeight

class OnScrollHeaderListener : RecyclerView.OnScrollListener() {

    private var scrollState = AbsListView.OnScrollListener.SCROLL_STATE_IDLE
    var cachedTopItemHolder: StickyAdapter.StickyViewHolder<*, *>? = null
        private set

    override fun onScrollStateChanged(recyclerView: RecyclerView, scrollState: Int) {
        this.scrollState = scrollState
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            return
        }
        val item = recyclerView.getChildAt(0) ?: return
        val completelyVisible = item.top >= 0
        if (item != cachedTopItemHolder?.itemView || completelyVisible) {
            cachedTopItemHolder?.header?.itemView?.translationY = 0f
            cachedTopItemHolder = item.tag as StickyAdapter.StickyViewHolder<*, *>
        }
        if (!completelyVisible) {
            moveHeader()
        }
    }

    private fun moveHeader() {
        val header = cachedTopItemHolder?.header?.itemView!!
        val item = cachedTopItemHolder?.itemView!!
        val headerHeight = header.absoluteHeight
        if (item.top < 0 && headerHeight <= item.bottom) {
            header.translationY = -item.top.toFloat()
        } else if (item.top < 0) {
            header.translationY = (item.height - headerHeight).toFloat()
        }
    }
}