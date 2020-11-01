package com.chekh.paysage.feature.widget.preentation.widgetboard.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.WidgetListAdapter

class WidgetsDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val adapter: WidgetListAdapter

    private val linearLayoutManager: LinearLayoutManager
    private var onOffsetChange: ((Int) -> Unit)? = null

    private val previewWidth by lazy {
        context.resources.getDimension(R.dimen.thumbnail_size)
    }

    private val widgetCardCommonPadding by lazy {
        context.resources.getDimension(R.dimen.widget_card_common_padding)
    }

    var scrollOffset
        get() = computeHorizontalScrollOffset()
        set(value) {
            linearLayoutManager.scrollToPositionWithOffset(0, -value)
        }

    init {
        overScrollMode = View.OVER_SCROLL_NEVER
        clipToPadding = false

        isNestedScrollingEnabled = false
        linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        layoutManager = linearLayoutManager
        adapter = WidgetListAdapter()
        setAdapter(adapter)

        setupOffsetListener()
    }

    fun setWidgets(
        widgets: List<WidgetModel>,
        isAnimate: Boolean = true,
        onUpdated: (() -> Unit)? = null
    ) {
        adapter.setWidgets(widgets, isAnimate, onUpdated)
        recalculateHeight(findMaxWidgetPreviewHeight(widgets))
    }

    private fun findMaxWidgetPreviewHeight(widgets: List<WidgetModel>) = widgets.maxOf { widget ->
        widget.previewImage?.let { (it.height * previewWidth / it.width).toDouble() } ?: -1.0
    }

    private fun recalculateHeight(maxWidgetPreviewHeight: Double) {
        layoutParams = layoutParams.apply {
            height = (maxWidgetPreviewHeight + widgetCardCommonPadding).toInt()
        }
    }

    fun setOffsetChangeListener(onOffsetChange: (Int) -> Unit) {
        this.onOffsetChange = onOffsetChange
    }

    private fun setupOffsetListener() {
        addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(view: RecyclerView, newState: Int) {
                    if (newState == SCROLL_STATE_IDLE) {
                        onOffsetChange?.invoke(scrollOffset)
                    }
                }
            }
        )
    }
}
