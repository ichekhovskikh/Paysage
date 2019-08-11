package com.chekh.paysage.ui.view.core.stickyheader

import android.graphics.Canvas
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.view.marginTop
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
            cachedTopHeader?.visibility = View.VISIBLE
            cachedTopHeader = findHeader(topChild)
            cachedTopHeader?.let {
                alwaysTopHeaderView.copyState(it)
                alwaysTopHeaderView.delegateEvents(it)
                alwaysTopHeaderView.translationY = (-topChild.marginTop).toFloat()
            }
        }
        moveHeader(topChild)
    }

    private fun moveHeader(topView: View) {
        val bottomPosition = topView.bottom
        val headerHeight = alwaysTopHeaderView.measuredHeight
        if (alwaysTopHeaderView.top < topView.top + headerHeight) {
            alwaysTopHeaderView.visibility = View.VISIBLE
            cachedTopHeader?.visibility = View.INVISIBLE
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
