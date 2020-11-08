package com.chekh.paysage.core.ui.view.diffable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

open class ListItemAdapter<T : ListItem> :
    ListAdapter<T, ListItemAdapter.ListViewHolder>(ListItemDiffCallback<T>()) {

    private var committedListener: ((List<T>) -> Unit)? = null

    var items: List<T> = emptyList()
        set(value) {
            field = value
            submitList(value) { committedListener?.invoke(field) }
        }

    fun setOnItemsCommittedListener(committedListener: (List<T>) -> Unit) {
        this.committedListener = committedListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ListViewHolder(
        LayoutInflater.from(parent.context).inflate(viewType, parent, false)
    )

    override fun onBindViewHolder(
        holder: ListViewHolder,
        position: Int,
        payloads: List<Any>
    ) {
        items[position].bind(holder, payloads)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
    }

    override fun getItemViewType(position: Int) = items[position].layout

    class ListViewHolder(override val containerView: View) :
        RecyclerView.ViewHolder(containerView), LayoutContainer

}
