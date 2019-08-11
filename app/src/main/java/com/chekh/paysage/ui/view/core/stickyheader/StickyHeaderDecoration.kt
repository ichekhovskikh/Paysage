package com.chekh.paysage.ui.view.core.stickyheader

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView

class StickyHeaderDecoration(private val alwaysTopHeaderView: StickyHeaderView) : RecyclerView.ItemDecoration() {

    private var cachedTopChild: View? = null
    private var cachedTopHeader: View? = null

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) return
        if (cachedTopChild != topChild) {
            cachedTopChild = topChild
            cachedTopHeader = findHeader(topChild)
            cachedTopHeader?.let {
                alwaysTopHeaderView.visibility = View.VISIBLE
                alwaysTopHeaderView.copyState(it)
                alwaysTopHeaderView.delegateEvents(it)
            }
        }
        moveHeader(topChild)
    }

    private fun moveHeader(topView: View) {
        val bottomPosition = topView.bottom
        val headerHeight = alwaysTopHeaderView.measuredHeight
        if (topView.top + headerHeight > 0) {
            alwaysTopHeaderView.translationY = 0f
        } else if (headerHeight >= bottomPosition) {
            alwaysTopHeaderView.translationY = (bottomPosition - headerHeight).toFloat()
        }
    }

    private fun findHeader(view: View): View? {
        if (view is ViewGroup) {
            return view.children.find { it is StickyHeaderView }
        }
        return null
    }
}
