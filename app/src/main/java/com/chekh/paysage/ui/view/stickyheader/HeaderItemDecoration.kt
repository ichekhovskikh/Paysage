package com.chekh.paysage.ui.view.stickyheader

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HeaderItemDecoration(private val mListener: StickyListener) : RecyclerView.ItemDecoration() {

    private var mHeaderHeight: Int? = null

    override fun onDrawOver(canvas: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(canvas, parent, state)
        val topChild = parent.getChildAt(0) ?: return

        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) return

        val headerPos = mListener.getHeaderPositionForItem(topChildPosition)
        if (headerPos == RecyclerView.NO_POSITION) return

        val currentHeader = getHeaderViewForItem(headerPos, parent)
        fixLayoutSize(parent, currentHeader)
        val contactPoint = currentHeader.bottom
        val childInContact = getChildInContact(parent, contactPoint, headerPos)

        if (childInContact != null && mListener.isHeader(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(canvas, currentHeader, childInContact)
            return
        }
        drawHeader(canvas, currentHeader)
    }

    private fun getHeaderViewForItem(headerPosition: Int, parent: RecyclerView): View {
        val layoutResId = mListener.getHeaderLayout(headerPosition)
        val header = LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        mListener.bindHeaderData(header, headerPosition)
        return header
    }

    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    private fun moveHeader(canvas: Canvas, currentHeader: View, nextHeader: View) {
        canvas.save()
        canvas.translate(0f, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(canvas)
        canvas.restore()
    }

    private fun getChildInContact(parent: RecyclerView, contactPoint: Int, currentHeaderPos: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            var heightTolerance = 0
            val child = parent.getChildAt(i)

            //measure height tolerance with child if child is another header
            if (currentHeaderPos != i) {
                val isChildHeader = mListener.isHeader(parent.getChildAdapterPosition(child))
                if (isChildHeader) {
                    heightTolerance = mHeaderHeight!! - child.height
                }
            }

            //add heightTolerance if child top be in display area
            val childBottomPosition: Int
            childBottomPosition = if (child.top > 0) child.bottom + heightTolerance else child.bottom

            if (childBottomPosition > contactPoint && child.top <= contactPoint) {
                // This child overlaps the contactPoint
                childInContact = child
                break
            }
        }
        return childInContact
    }

    /**
     * Properly measures and layouts the top sticky header.
     *
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {
        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec =
            ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(
            heightSpec,
            parent.paddingTop + parent.paddingBottom,
            view.layoutParams.height
        )
        view.measure(childWidthSpec, childHeightSpec)
        mHeaderHeight = view.measuredHeight
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }
}
