package com.chekh.paysage.core.ui.view.flow

import android.content.Context
import android.graphics.PointF
import android.graphics.Rect
import android.os.Parcelable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import kotlinx.android.parcel.Parcelize
import java.lang.IllegalArgumentException
import kotlin.math.max

class FlowLayoutManager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : RecyclerView.LayoutManager() {

    var spanCount = SPAN_COUNt_DEFAULT
        set(value) {
            field = value
            rectList.clear()
            verticalOffset = 0
            removeAllViews()
            requestLayout()
        }

    init {
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.FlowLayoutManager,
            defStyleAttr,
            0
        )
        if (attributes.hasValue(R.styleable.FlowLayoutManager_spanCount)) {
            spanCount = attributes.getInteger(
                R.styleable.FlowLayoutManager_spanCount,
                SPAN_COUNt_DEFAULT
            )
        }
        attributes.recycle()
    }

    private val tag = javaClass.simpleName

    private val rectList = mutableListOf<Rect>()
    private val viewToItemIndexMap = mutableMapOf<View, Int>()
    private val preLayoutChildIndexes = mutableSetOf<Int>()
    private val visibleChildIndexes = mutableSetOf<Int>()
    private var attachedRecycler: RecyclerView? = null
    private var verticalOffset = 0

    private val availableWidth: Int
        get() = width - paddingStart - paddingEnd

    private val availableHeight: Int
        get() = height - paddingTop - paddingBottom

    private val columnWidth: Int
        get() = availableWidth / spanCount

    private val rowHeight: Int
        get() = columnWidth

    private val totalChildrenHeight: Int
        get() = rectList.maxOfOrNull { it.bottom } ?: 0

    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)
        attachedRecycler = view
    }

    override fun onDetachedFromWindow(view: RecyclerView?, recycler: RecyclerView.Recycler?) {
        super.onDetachedFromWindow(view, recycler)
        attachedRecycler = null
    }

    private fun getItemSize(item: FlowListItem) =
        item.columnCount * columnWidth to item.rowCount * rowHeight

    private fun getItemCoordinates(item: FlowListItem) =
        item.columnIndex * columnWidth to item.rowIndex * rowHeight

    override fun generateDefaultLayoutParams() = RecyclerView.LayoutParams(
        RecyclerView.LayoutParams.WRAP_CONTENT,
        RecyclerView.LayoutParams.WRAP_CONTENT
    )

    override fun supportsPredictiveItemAnimations() = true

    override fun onItemsRemoved(recyclerView: RecyclerView, positionStart: Int, itemCount: Int) {
        preLayoutChildIndexes.removeAll { it in positionStart until positionStart + itemCount }
        val newPreLayoutIndexes = preLayoutChildIndexes.map {
            if (it < positionStart) it else it - itemCount
        }
        preLayoutChildIndexes.clear()
        preLayoutChildIndexes.addAll(newPreLayoutIndexes)
    }

    override fun onItemsUpdated(recyclerView: RecyclerView, from: Int, itemCount: Int) {
        preLayoutChildIndexes.addAll(from until from + itemCount)
    }

    override fun onAdapterChanged(
        oldAdapter: RecyclerView.Adapter<*>?,
        newAdapter: RecyclerView.Adapter<*>?
    ) {
        rectList.clear()
        removeAllViews()
    }

    override fun findViewByPosition(position: Int): View? =
        viewToItemIndexMap.entries.find { it.value == position }?.key

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        if (itemCount <= 0) {
            detachAndScrapAttachedViews(recycler)
            return
        }
        if (state.isPreLayout) {
            preLayoutChildren(recycler)
            rectList.clear()
            return
        }
        if (childCount <= 0 || rectList.size == 0) {
            updateLayout()
            updateScrollOffset()
        }
        updateVisibleChildren()
        detachAndScrapAttachedViews(recycler)
        layoutChildren(recycler)
    }

    private fun updateLayout() {
        rectList.clear()
        val adapter = attachedRecycler?.adapter as? FlowListAdapter
            ?: throw IllegalArgumentException("Cannot display without FlowListAdapter")
        adapter.currentList.forEach { item ->
            val (width, height) = getItemSize(item)
            val (x, y) = getItemCoordinates(item)
            rectList.add(Rect(x, y, x + width, y + height))
        }
    }

    private fun updateScrollOffset() {
        if (verticalOffset > totalChildrenHeight - availableHeight) {
            verticalOffset = max(totalChildrenHeight - availableHeight, 0)
        }
    }

    private fun updateVisibleChildren() {
        visibleChildIndexes.clear()
        val viewport = Rect(0, verticalOffset, availableWidth, verticalOffset + availableHeight)
        rectList.forEachIndexed { index, viewRect ->
            if (viewport.intersects(viewRect)) {
                visibleChildIndexes.add(index)
            }
        }
    }

    private fun preLayoutChildren(recycler: RecyclerView.Recycler) {
        preLayoutChildIndexes.forEach { itemIndex ->
            attachNewViewForPosition(recycler, itemIndex)
        }
        preLayoutChildIndexes.clear()
    }

    private fun layoutChildren(recycler: RecyclerView.Recycler) {
        val viewCache = detachAllChildren()
        visibleChildIndexes.union(preLayoutChildIndexes).forEach { itemIndex ->
            val cachedView = viewCache[itemIndex]
            if (cachedView == null) {
                attachNewViewForPosition(recycler, itemIndex)
            } else {
                attachView(cachedView)
                viewCache.remove(itemIndex)
            }
        }
        preLayoutChildIndexes.clear()
        viewCache.forEach { entry ->
            viewToItemIndexMap.remove(entry.value)
            recycler.recycleView(entry.value)
        }
    }

    private fun attachNewViewForPosition(recycler: RecyclerView.Recycler, itemIndex: Int) {
        val view = recycler.getViewForPosition(itemIndex)
        val rect = rectList[itemIndex]
        view.layoutParams.width = rect.width()
        view.layoutParams.height = rect.height()
        addView(view)
        viewToItemIndexMap[view] = itemIndex
        measureChildWithMargins(view, 0, 0)
        val top = rect.top - verticalOffset
        val bottom = rect.bottom - verticalOffset
        layoutDecorated(view, rect.left, top, rect.right, bottom)
    }

    private fun detachAllChildren(): MutableMap<Int, View> {
        val viewCache = mutableMapOf<Int, View>()
        for (childIndex in 0 until childCount) {
            val view = getChildAt(childIndex) ?: continue
            val itemIndex = viewToItemIndexMap[view] ?: continue
            viewCache[itemIndex] = view
        }
        viewCache.forEach { detachView(it.value) }
        return viewCache
    }

    override fun canScrollHorizontally() = false

    override fun canScrollVertically(): Boolean {
        return totalChildrenHeight > availableHeight
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler,
        state: RecyclerView.State
    ): Int {
        if (itemCount <= 0 || totalChildrenHeight < availableHeight) return 0

        val delta = when {
            dy > 0 && verticalOffset + availableHeight + dy > totalChildrenHeight -> {
                totalChildrenHeight - verticalOffset - availableHeight
            }
            dy <= 0 && verticalOffset + dy <= 0 -> -verticalOffset
            else -> dy
        }
        offsetChildrenVertical(-delta)
        verticalOffset += delta
        updateVisibleChildren()
        layoutChildren(recycler)
        return delta
    }

    override fun scrollToPosition(position: Int) {
        if (position < 0 || position >= rectList.size) {
            Log.e(tag, "Cannot scroll to $position, item count is ${rectList.size - 1}")
            return
        }
        val target = rectList[position]
        val screenBottom = verticalOffset + availableHeight
        if (target.top >= verticalOffset && target.bottom <= screenBottom) {
            Log.d("scrollToPosition", "${target.top} don't move")
            return
        }
        val targetHeight = target.bottom - target.top
        verticalOffset = if (target.top < verticalOffset || targetHeight > availableHeight) {
            target.top
        } else {
            target.bottom - availableHeight
        }
        removeAllViews()
        requestLayout()
    }

    override fun smoothScrollToPosition(
        recyclerView: RecyclerView?,
        state: RecyclerView.State?,
        position: Int
    ) {
        recyclerView ?: return
        if (position < 0 || position >= rectList.size) {
            Log.e(tag, "Cannot scroll to $position, item count is ${rectList.size}")
            return
        }

        val scroller = object : LinearSmoothScroller(recyclerView.context) {

            override fun computeScrollVectorForPosition(targetPosition: Int): PointF {
                val target = rectList[targetPosition]
                return PointF(0F, target.top.toFloat())
            }
        }
        scroller.targetPosition = position
        startSmoothScroll(scroller)
    }

    private fun Rect.intersects(other: Rect): Boolean {
        return intersects(other.left, other.top, other.right, other.bottom)
    }

    override fun onSaveInstanceState() =
        SavedState(spanCount, verticalOffset, preLayoutChildIndexes, visibleChildIndexes)

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) return
        verticalOffset = state.verticalOffset
        preLayoutChildIndexes.addAll(state.preLayoutChildIndexes)
        visibleChildIndexes.addAll(state.visibleChildIndexes)
        spanCount = state.spanCount
    }

    @Parcelize
    data class SavedState(
        internal val spanCount: Int,
        internal val verticalOffset: Int,
        internal val preLayoutChildIndexes: Set<Int>,
        internal val visibleChildIndexes: Set<Int>
    ) : Parcelable

    private companion object {
        const val SPAN_COUNt_DEFAULT = 4
    }
}
