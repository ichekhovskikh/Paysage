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

    fun scrollToTopHeader() {
        val holder = scrollListener.cachedTopItemHolder
        holder?.itemView?.let {
            val position = getChildLayoutPosition(it)
            scrollToPosition(position)
        }
    }
}