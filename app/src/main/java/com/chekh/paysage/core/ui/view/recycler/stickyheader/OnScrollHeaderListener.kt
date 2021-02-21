package com.chekh.paysage.core.ui.view.recycler.stickyheader

import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.core.extension.absoluteHeight
import com.chekh.paysage.core.ui.view.recycler.stickyheader.StickyAdapter.StickyViewHolder

class OnScrollHeaderListener : RecyclerView.OnScrollListener() {

    private var cachedTopItemHolder: StickyViewHolder<*, *>? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val topView = recyclerView.getChildAt(0) ?: return
        val holder = recyclerView.findContainingViewHolder(topView)
        if (holder !is StickyViewHolder<*, *>) return
        val completelyVisible = holder.itemView.top >= 0
        if (holder != cachedTopItemHolder || completelyVisible) {
            cachedTopItemHolder?.header?.itemView?.translationY = 0f
            cachedTopItemHolder = holder
        }
        if (!completelyVisible) {
            moveHeader()
        }
    }

    private fun moveHeader() {
        val topItemHolder = cachedTopItemHolder ?: return
        val header = topItemHolder.header.itemView
        val item = topItemHolder.itemView
        val headerHeight = header.absoluteHeight
        if (item.top < 0 && headerHeight <= item.bottom) {
            header.translationY = -item.top.toFloat()
        } else if (item.top < 0) {
            header.translationY = (item.height - headerHeight).toFloat()
        }
    }
}
