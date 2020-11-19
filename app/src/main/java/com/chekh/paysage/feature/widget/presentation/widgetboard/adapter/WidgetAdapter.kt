package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.differ.WidgetDiffCallback
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.holder.WidgetViewHolder

class WidgetAdapter : ListAdapter<WidgetModel, WidgetViewHolder>(WidgetDiffCallback()) {

    private var recycler: RecyclerView? = null
    private var itemAnimator: RecyclerView.ItemAnimator? = null

    fun setWidgets(
        widgets: List<WidgetModel>,
        isAnimate: Boolean = true,
        onUpdated: (() -> Unit)? = null
    ) {
        setAnimate(isAnimate)
        submitList(widgets, onUpdated)
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        super.onAttachedToRecyclerView(recycler)
        this.recycler = recycler
        this.itemAnimator = recycler.itemAnimator
    }

    private fun setAnimate(isAnimate: Boolean) {
        recycler?.itemAnimator = if (isAnimate) itemAnimator else null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = WidgetViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_widget_card, parent, false)
    )

    override fun onBindViewHolder(holder: WidgetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
