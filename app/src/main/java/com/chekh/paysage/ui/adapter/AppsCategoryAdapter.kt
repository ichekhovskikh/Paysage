package com.chekh.paysage.ui.adapter

import android.view.ViewGroup
import com.chekh.paysage.model.AppsGroupByCategory
import com.chekh.paysage.ui.view.app.AppsDataView
import com.chekh.paysage.ui.view.app.AppsHeaderView
import com.chekh.paysage.ui.view.core.stickyheader.StickyRecyclerAdapter

class AppsCategoryAdapter : StickyRecyclerAdapter<AppsHeaderView, AppsDataView>() {

    private lateinit var items: List<AppsGroupByCategory>

    fun setAppsCategories(appsCategory: List<AppsGroupByCategory>) {
        items = appsCategory.sortedBy { it.category.position }
        notifyDataSetChanged()
    }

    override fun onCreateHeaderView(parent: ViewGroup, viewType: Int): AppsHeaderView {
        return AppsHeaderView(parent.context)
    }

    override fun onCreateDataView(parent: ViewGroup, viewType: Int): AppsDataView {
        return AppsDataView(parent.context)
    }

    override fun onBindHeaderView(view: AppsHeaderView, position: Int) {
        view.bind(items[position].category)
    }

    override fun onBindDataView(view: AppsDataView, position: Int) {
        view.bind(items[position].apps)
    }

    override fun getItemCount(): Int {
        return if (::items.isInitialized) items.size else 0
    }
}