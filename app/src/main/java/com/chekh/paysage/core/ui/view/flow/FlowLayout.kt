package com.chekh.paysage.core.ui.view.flow

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Rect
import android.graphics.RectF
import android.os.Parcelable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.animation.doOnEnd
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.view.flow.items.FlowListItem
import com.chekh.paysage.core.ui.view.recycler.diffable.ListItemAdapter
import kotlinx.android.parcel.Parcelize
import java.lang.ref.WeakReference
import java.util.concurrent.Flow
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class FlowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes), FlowAdapterObserver {

    private var columnCount = COLUMN_COUNT_DEFAULT
    private var rowCount = ROW_COUNt_DEFAULT

    private val children = mutableMapOf<String, WeakReference<View>>()
    private val animators = mutableMapOf<String, ValueAnimator>()

    var adapter: FlowListAdapter? = null
        set(value) {
            val previousList = field?.currentList
            val currentList = value?.currentList ?: emptyList()
            field?.unregisterAdapterObserver(this)
            value?.registerAdapterObserver(this)
            field = value
            if (previousList == null) {
                onCurrentListChangedWithoutAnimation(currentList)
            } else {
                onCurrentListChanged(previousList, currentList)
            }
        }

    private val columnWidth: Int
        get() = (width - paddingStart - paddingEnd) / columnCount

    private val rowHeight: Int
        get() = (height - paddingTop - paddingBottom) / rowCount

    private val availableWidth: Int
        get() = columnWidth * columnCount

    private val availableHeight: Int
        get() = rowHeight * rowCount

    init {
        val attributes = context.obtainStyledAttributes(
            attrs,
            R.styleable.FlowLayout,
            defStyleAttr,
            defStyleRes
        )
        if (attributes.hasValue(R.styleable.FlowLayout_columnCount)) {
            columnCount =
                attributes.getInteger(R.styleable.FlowLayout_columnCount, COLUMN_COUNT_DEFAULT)
        }
        if (attributes.hasValue(R.styleable.FlowLayout_rowCount)) {
            rowCount = attributes.getInteger(R.styleable.FlowLayout_rowCount, ROW_COUNt_DEFAULT)
        }
        attributes.recycle()
    }

    private fun onCurrentListChangedWithoutAnimation(currentList: List<FlowListItem>) {
        children.clear()
        animators.clear()
        removeAllViews()
        post {
            mutableListOf<FlowChange>()
                .apply { addAddedChanges(currentList) }
                .forEach {
                    val change = it as? FlowChange.Added ?: return@forEach
                    addViewInternalIfNeed(change.id, change.view, change.bounds)
                }
        }
    }

    override fun onCurrentListChanged(
        previousList: List<FlowListItem>,
        currentList: List<FlowListItem>
    ) {
        if (previousList == currentList) return
        val addedItems = currentList.filter { currentItem ->
            children[currentItem.id] == null && previousList.all { previousItem ->
                !previousItem.isSameAs(currentItem)
            }
        }
        val removedItems = previousList.filter { previousItem ->
            currentList.all { currentItem -> !currentItem.isSameAs(previousItem) }
        }
        val movedItems = currentList.filter { currentItem ->
            children[currentItem.id] != null || previousList.any { previousItem ->
                currentItem.isSameAs(previousItem) &&
                    getItemBounds(currentItem) != getItemBounds(previousItem)
            }
        }
        val changes = mutableListOf<FlowChange>().apply {
            addAddedChanges(addedItems)
            addRemovedChanges(removedItems)
            addMovedChanges(movedItems)
        }
        animateChanges(changes)
    }

    private fun MutableList<FlowChange>.addAddedChanges(items: List<FlowListItem>) {
        items.forEach { item ->
            val bounds = getItemBounds(item)
            val alpha = when (bounds.left < availableWidth && bounds.top < availableHeight) {
                true -> 1f
                else -> 0f
            }
            val view = inflate(context, item.layout, null)
            item.bind(ListItemAdapter.ListViewHolder(view))
            add(FlowChange.Added(item.id, view, alpha, bounds))
        }
    }

    private fun MutableList<FlowChange>.addRemovedChanges(items: List<FlowListItem>) {
        items.forEach { item ->
            val view = children[item.id]?.get()
            view?.let { add(FlowChange.Removed(item.id, view)) }
        }
    }

    private fun MutableList<FlowChange>.addMovedChanges(items: List<FlowListItem>) {
        items.forEach { item ->
            val view = children[item.id]?.get()
            val bounds = getItemBounds(item)
            val alpha = when (bounds.left < availableWidth && bounds.top < availableHeight) {
                true -> 1f
                else -> 0f
            }
            view?.let { add(FlowChange.Moved(item.id, view, alpha, bounds)) }
        }
    }

    fun setSize(columnCount: Int = this.columnCount, rowCount: Int = this.rowCount) {
        this.columnCount = columnCount
        this.rowCount = rowCount
        val currentList = adapter?.currentList
        if (currentList.isNullOrEmpty()) return
        val changes = mutableListOf<FlowChange>().apply {
            addMovedChanges(currentList)
        }
        animateChanges(changes)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (!changed) return
        val currentList = adapter?.currentList
        if (currentList.isNullOrEmpty()) return
        val changes = mutableListOf<FlowChange>().apply {
            addMovedChanges(currentList)
        }
        animateChanges(changes)
    }

    fun getOccupiedCells(rect: RectF): Rect {
        val height = max(1, round(rect.height() / rowHeight).toInt())
        val width = max(1, round(rect.width() / columnWidth).toInt())
        var top = 0
        var bottom = rowCount
        var left = 0
        var right = columnCount

        if (height < rowCount) {
            top = round((rect.top - paddingTop) / rowHeight).toInt()
            bottom = top + height
        }
        if (top < 0) {
            bottom -= top
            top = 0
        } else if (bottom > rowCount) {
            top -= bottom - rowCount
            bottom = rowCount
        }

        if (width < columnCount) {
            left = round((rect.left - paddingStart) / columnWidth).toInt()
            right = left + width
        }
        if (left < 0) {
            right -= left
            left = 0
        } else if (right > columnCount) {
            left -= right - columnCount
            right = columnCount
        }
        return Rect(left, top, right, bottom)
    }

    fun getItemBounds(item: FlowListItem): Rect {
        val x = item.getX(columnWidth)
        val y = item.getY(rowHeight)
        val width = item.getWidth(columnWidth)
        val height = item.getHeight(rowHeight)

        val left = max(x, 0)
        val right = when (left >= availableWidth) {
            true -> left + columnWidth
            else -> min(left + width, availableWidth)
        }
        val top = max(y, 0)
        val bottom = when (top >= availableHeight) {
            true -> top + rowHeight
            else -> min(top + height, availableHeight)
        }
        return Rect(left, top, right, bottom)
    }

    private fun animateChanges(changes: List<FlowChange>) {
        if (changes.isEmpty()) return
        val changeGroups = changes.groupBy { it.id }
        for ((_, changeGroup) in changeGroups) {
            val change = changeGroup.minByOrNull { it.priority } ?: continue
            if (change is FlowChange.Added) {
                addAnimatingViewInternalIfNeed(change)
            }
            val view = change.view
            val animator = animators[change.id]
            startChangeAnimator(view, animator, change)
        }
    }

    private fun startChangeAnimator(
        view: View,
        animator: ValueAnimator?,
        change: FlowChange
    ) {
        animator ?: return
        animator.pause()
        val fromAlpha = view.alpha
        val fromLeft = view.translationX
        val fromTop = view.translationY
        val fromWidth = view.layoutParams.width
        val fromHeight = view.layoutParams.height

        animator.apply {
            removeAllListeners()
            removeAllUpdateListeners()
            addUpdateListener {
                val animatedValue = it.animatedValue as Float
                when (change) {
                    is FlowChange.Added -> {
                        if (view.alpha == change.toAlpha) animator.cancel()
                        val dAlpha = change.toAlpha - fromAlpha
                        view.alpha = fromAlpha + dAlpha * animatedValue
                    }
                    is FlowChange.Removed -> {
                        if (view.alpha == 0f) animator.cancel()
                        view.alpha = fromAlpha - fromAlpha * animatedValue
                    }
                    is FlowChange.Moved -> {
                        if (view.alpha != change.toAlpha) {
                            val dAlpha = change.toAlpha - fromAlpha
                            view.alpha = fromAlpha + dAlpha * animatedValue
                        }
                        val dLeft = (change.toBounds.left - fromLeft) * animatedValue
                        val dTop = (change.toBounds.top - fromTop) * animatedValue
                        view.translationX = fromLeft + dLeft
                        view.translationY = fromTop + dTop
                        view.layoutParams = view.layoutParams.apply {
                            val dHeight = (change.toBounds.height() - fromHeight) * animatedValue
                            val dWidth = (change.toBounds.width() - fromWidth) * animatedValue
                            height = fromHeight + dHeight.toInt()
                            width = fromWidth + dWidth.toInt()
                        }
                    }
                }
            }
            doOnEnd {
                removeAllListeners()
                removeAllUpdateListeners()
                if (change is FlowChange.Removed) {
                    children.remove(change.id)
                    animators.remove(change.id)
                    removeView(change.view)
                }
            }
            start()
        }
    }

    private fun addAnimatingViewInternalIfNeed(change: FlowChange.Added) {
        if (children.containsKey(change.id)) return
        animators[change.id] = ValueAnimator
            .ofFloat(0f, 1f)
            .setDuration(ANIMATION_DURATION)
        change.view.alpha = 1 - change.toAlpha
        addViewInternalIfNeed(change.id, change.view, change.bounds)
    }

    private fun addViewInternalIfNeed(viewId: String, view: View, bounds: Rect) {
        if (children.containsKey(viewId)) return
        children[viewId] = WeakReference(view)
        addView(view)
        view.translationX = bounds.left.toFloat()
        view.translationY = bounds.top.toFloat()
        view.layoutParams = view.layoutParams.apply {
            height = bounds.height()
            width = bounds.width()
        }
    }

    override fun onSaveInstanceState() = SavedState(
        super.onSaveInstanceState(),
        columnCount,
        rowCount
    )

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state !is SavedState) {
            super.onRestoreInstanceState(state)
            return
        }
        super.onRestoreInstanceState(state.superState)
        this.columnCount = state.columnCount
        this.rowCount = state.rowCount

        val currentList = adapter?.currentList
        if (!currentList.isNullOrEmpty()) {
            onCurrentListChangedWithoutAnimation(currentList)
        }
    }

    @Parcelize
    data class SavedState(
        val superState: Parcelable?,
        val columnCount: Int,
        val rowCount: Int
    ) : Parcelable

    private companion object {
        const val COLUMN_COUNT_DEFAULT = 4
        const val ROW_COUNt_DEFAULT = 5
        const val ANIMATION_DURATION = 250L
    }
}
