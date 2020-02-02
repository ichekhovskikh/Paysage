package com.chekh.paysage.ui.view.core.stickyheader

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

    fun scrollToTopHeader(onCancel: (() -> Unit)? = null) {
        val holder = scrollListener.cachedTopItemHolder
        holder?.itemView?.let {
            scrollListener.onCancelScrollAction = onCancel
            val position = getChildLayoutPosition(it)
            smoothScrollToPosition(position)
        }
    }

    /**
     * hack to perform a artificial scroll after resizing StickyRecyclerView
     */
    fun onExpandedItemStateChanged(isExpanded: Boolean) {
        if (!isExpanded) {
            post { scrollListener.onScrolled(this, 1, 0) }
        }
    }
}