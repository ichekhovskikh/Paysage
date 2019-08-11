package com.chekh.paysage.ui.view.core.stickyheader

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView

class StickyRecyclerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    lateinit var recycler: RecyclerView private set
    private lateinit var header: StickyHeaderView

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (index in 0 until childCount) {
            val child = getChildAt(index)
            if (child is RecyclerView) {
                recycler = child
            } else if (child is StickyHeaderView) {
                header = child
            }
        }
        onStartInitRecycler()
    }

    private fun onStartInitRecycler() {
        if (::recycler.isInitialized && ::header.isInitialized) {
            recycler.addItemDecoration(StickyHeaderDecoration(header))
        }
    }
}