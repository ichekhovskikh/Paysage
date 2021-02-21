package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chekh.paysage.core.ui.view.recycler.stickyheader.StickyAdapter
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.payload.WidgetGroupStateChanged
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.payload.WidgetGroupWidgetsChanged
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.differ.WidgetsGroupDiffCallback
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.holder.WidgetGroupDataViewHolder
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.holder.WidgetGroupHeaderViewHolder
import com.chekh.paysage.feature.widget.presentation.widgetboard.model.WidgetGroupModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.view.WidgetGroupDataView
import com.chekh.paysage.feature.widget.presentation.widgetboard.view.WidgetGroupHeaderView

class WidgetGroupAdapter(
    private val onGroupScrollOffsetChanged: (Int, String) -> Unit,
    private val onStartDragAndDrop: ((View, WidgetModel) -> Unit)? = null
) : StickyAdapter<WidgetGroupModel, WidgetGroupHeaderViewHolder, WidgetGroupDataViewHolder>(
    WidgetsGroupDiffCallback()
) {

    private val sharedPool = RecycledViewPool().apply { setMaxRecycledViews(0, SHARED_POOL_SIZE) }
    private var recycler: RecyclerView? = null

    fun setWidgetGroups(
        widgetGroups: List<WidgetGroupModel>,
        isAnimate: Boolean = false
    ) {
        submitList(widgetGroups)
        if (isAnimate) {
            recycler?.scheduleLayoutAnimation()
        }
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        super.onAttachedToRecyclerView(recycler)
        this.recycler = recycler
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): WidgetGroupHeaderViewHolder =
        WidgetGroupHeaderViewHolder(WidgetGroupHeaderView(parent.context))

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): WidgetGroupDataViewHolder =
        WidgetGroupDataViewHolder(
            view = WidgetGroupDataView(parent.context).apply { setRecycledViewPool(sharedPool) },
            onScrollChanged = onGroupScrollOffsetChanged,
            onStartDragAndDrop = onStartDragAndDrop
        )

    override fun onBindHeaderViewHolder(
        parent: ViewGroup,
        headerHolder: WidgetGroupHeaderViewHolder,
        position: Int,
        payloads: List<Any>?
    ) {
        val item = getItem(position)
        headerHolder.bind(item)
    }

    override fun onBindContentViewHolder(
        parent: ViewGroup,
        contentHolder: WidgetGroupDataViewHolder,
        position: Int,
        payloads: List<Any>?
    ) {
        val item = getItem(position)
        if (payloads.isNullOrEmpty()) {
            contentHolder.bind(item)
            return
        }
        for (payload in payloads) {
            when (payload) {
                is WidgetGroupStateChanged -> {
                    contentHolder.setScrollOffset(payload.scrollOffset)
                }
                is WidgetGroupWidgetsChanged -> {
                    contentHolder.setWidgets(payload.widgets)
                }
            }
        }
    }

    companion object {
        private const val SHARED_POOL_SIZE = 20
    }
}
