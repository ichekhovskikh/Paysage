package com.chekh.paysage.core.ui.view.stickyheader

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter

class StickyRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val scrollListener = OnScrollHeaderListener()
    private val overScrollDecorator: VerticalOverScrollBounceEffectDecorator

    init {
        addOnScrollListener(scrollListener)
        overScrollDecorator = VerticalOverScrollBounceEffectDecorator(
            RecyclerViewOverScrollDecorAdapter(this)
        )
    }

    fun setBouncing(isBouncing: Boolean) {
        val touchListener = if (isBouncing) overScrollDecorator else null
        setOnTouchListener(touchListener)
    }

    fun smoothScrollToHeader(position: Int) {
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
