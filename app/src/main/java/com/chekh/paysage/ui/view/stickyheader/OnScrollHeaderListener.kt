package com.chekh.paysage.ui.view.stickyheader

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.chekh.paysage.extension.absoluteHeight
import com.chekh.paysage.ui.view.stickyheader.StickyAdapter.StickyViewHolder

class OnScrollHeaderListener : RecyclerView.OnScrollListener() {

    var cachedTopItemHolder: StickyViewHolder<*, *>? = null
        private set

    var onCancelScrollAction: (() -> Unit)? = null

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val holder = recyclerView.getChildAt(0).tag as StickyViewHolder<*, *>? ?: return
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

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == SCROLL_STATE_IDLE) {
            onCancelScrollAction?.invoke()
            onCancelScrollAction = null
        }
    }
}