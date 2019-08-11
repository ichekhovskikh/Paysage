package com.chekh.paysage.ui.adapter

import android.view.ViewGroup
import com.chekh.paysage.model.AppsGroupByCategory
import com.chekh.paysage.ui.view.app.AppsDataView
import com.chekh.paysage.ui.view.app.AppsHeaderView
import com.chekh.paysage.ui.view.core.stickyheader.StickyRecyclerAdapter

class AppsCategoryAdapter : StickyRecyclerAdapter<AppsHeaderView, AppsDataView>() {

    private lateinit var items: List<AppsGroupByCategory>
    private val expandedStates = mutableMapOf<String, Boolean>()

    fun setAppsCategories(appsCategory: List<AppsGroupByCategory>) {
        items = appsCategory.sortedBy { it.category.position }
        notifyDataSetChanged()
    }

    fun collapseAll() {
        expandedStates.clear()
        notifyDataSetChanged()
    }

    override fun onCreateHeaderView(parent: ViewGroup, viewType: Int): AppsHeaderView {
        return AppsHeaderView(parent.context)
    }

    override fun onCreateDataView(parent: ViewGroup, viewType: Int): AppsDataView {
        return AppsDataView(parent.context)
    }

    override fun onBindViews(header: AppsHeaderView, data: AppsDataView, position: Int) {
        val item = items[position]
        val categoryId = item.category.id
        header.bind(item.category)
        data.bind(item.apps)
        expandedStates[categoryId]?.let {
            header.arrowItemView.nonAnimationExpand(it)
            data.expand(it)
        }
        header.arrowItemView.setOnExpandedClickListener { expanded ->
            expandedStates[categoryId] = expanded
            data.animationExpand(expanded)
        }
    }

    override fun getItemCount(): Int {
        return if (::items.isInitialized) items.size else 0
    }
}