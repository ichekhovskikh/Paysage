package com.chekh.paysage.ui.view.core.stickyheader

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.chekh.paysage.R

abstract class StickyRecyclerAdapter<Header : View, Data : View> :
    RecyclerView.Adapter<StickyRecyclerAdapter.StickyViewHolder<Header, Data>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickyViewHolder<Header, Data> {
        val headerView = onCreateHeaderView(parent, viewType)
        val dataView = onCreateDataView(parent, viewType)
        return StickyViewHolder(parent, headerView, dataView)
    }

    override fun onBindViewHolder(holder: StickyViewHolder<Header, Data>, position: Int) {
        onBindHeaderView(holder.headerView, position)
        onBindDataView(holder.dataView, position)
    }

    abstract fun onCreateHeaderView(parent: ViewGroup, viewType: Int): Header

    abstract fun onCreateDataView(parent: ViewGroup, viewType: Int): Data

    abstract fun onBindHeaderView(view: Header, position: Int)

    abstract fun onBindDataView(view: Data, position: Int)

    class StickyViewHolder<Header : View, Data : View>(parent: ViewGroup, val headerView: Header, val dataView: Data) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_sticky_view_holder, parent, false)
        ) {
        init {
            val view = itemView as ViewGroup
            view.addView(headerView)
            view.addView(dataView)
        }
    }
}