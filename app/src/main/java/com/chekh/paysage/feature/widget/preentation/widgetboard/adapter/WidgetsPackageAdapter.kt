package com.chekh.paysage.feature.widget.preentation.widgetboard.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chekh.paysage.core.ui.view.stickyheader.StickyAdapter
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.data.WidgetsPackageStateChanged
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.data.WidgetsPackageWidgetsChanged
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.differ.WidgetsGroupByPackageDiffCallback
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.holder.WidgetsDataViewHolder
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.holder.WidgetsHeaderViewHolder
import com.chekh.paysage.feature.widget.preentation.widgetboard.model.ScrollableWidgetsGroupByPackageModel
import com.chekh.paysage.feature.widget.preentation.widgetboard.view.WidgetsDataView
import com.chekh.paysage.feature.widget.preentation.widgetboard.view.WidgetsHeaderView

class WidgetsPackageAdapter(
    private val onScrollPackageChange: (Int, String) -> Unit
) : StickyAdapter<ScrollableWidgetsGroupByPackageModel, WidgetsHeaderViewHolder, WidgetsDataViewHolder>(
    WidgetsGroupByPackageDiffCallback()
) {

    private val sharedPool = RecycledViewPool().apply { setMaxRecycledViews(0, SHARED_POOL_SIZE) }
    private var recycler: RecyclerView? = null

    fun setWidgetPackages(
        widgetsGroupByPackage: List<ScrollableWidgetsGroupByPackageModel>,
        isAnimate: Boolean = false
    ) {
        submitList(widgetsGroupByPackage)
        if (isAnimate) {
            recycler?.scheduleLayoutAnimation()
        }
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        super.onAttachedToRecyclerView(recycler)
        this.recycler = recycler
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): WidgetsHeaderViewHolder =
        WidgetsHeaderViewHolder(WidgetsHeaderView(parent.context))

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): WidgetsDataViewHolder =
        WidgetsDataViewHolder(
            view = WidgetsDataView(parent.context).apply { setRecycledViewPool(sharedPool) },
            onScrollChange = onScrollPackageChange
        )

    override fun onBindHeaderViewHolder(
        parent: ViewGroup,
        headerHolder: WidgetsHeaderViewHolder,
        position: Int,
        payloads: List<Any>?
    ) {
        val item = getItem(position)
        headerHolder.bind(item)
    }

    override fun onBindContentViewHolder(
        parent: ViewGroup,
        contentHolder: WidgetsDataViewHolder,
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
                is WidgetsPackageStateChanged -> {
                    contentHolder.setScrollOffset(payload.scrollOffset)
                }
                is WidgetsPackageWidgetsChanged -> {
                    contentHolder.setWidgets(payload.widgets)
                }
            }
        }
    }

    companion object {
        private const val SHARED_POOL_SIZE = 20
    }
}
