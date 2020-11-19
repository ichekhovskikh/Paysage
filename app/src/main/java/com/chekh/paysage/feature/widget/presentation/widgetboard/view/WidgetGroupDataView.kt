package com.chekh.paysage.feature.widget.presentation.widgetboard.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.WidgetAdapter

class WidgetGroupDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val adapter: WidgetAdapter

    private val linearLayoutManager: LinearLayoutManager
    private var onOffsetChange: ((Int) -> Unit)? = null

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
        adapter = WidgetAdapter()
        setAdapter(adapter)

        setupOffsetListener()
    }

    fun setWidgets(
        widgets: List<WidgetModel>,
        isAnimate: Boolean = true,
        onUpdated: (() -> Unit)? = null
    ) {
        adapter.setWidgets(widgets, isAnimate, onUpdated)
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
