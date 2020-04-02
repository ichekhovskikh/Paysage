package com.chekh.paysage.feature.home.apps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.feature.home.apps.model.AppsDataViewState
import com.chekh.paysage.feature.home.apps.model.AppsGroupByCategory
import com.chekh.paysage.feature.home.apps.model.StateableAppsGroupByCategory
import com.chekh.paysage.feature.home.apps.tool.restore
import com.chekh.paysage.feature.home.apps.tool.save
import com.chekh.paysage.feature.home.apps.view.AppsDataView
import com.chekh.paysage.feature.home.apps.view.AppsHeaderView
import com.chekh.paysage.ui.view.ArrowItemView
import com.chekh.paysage.ui.view.stickyheader.StickyAdapter
import com.chekh.paysage.ui.view.stickyheader.StickyRecyclerView

class AppsCategoryAdapter :
    StickyAdapter<AppsCategoryAdapter.AppsHeaderViewHolder, AppsCategoryAdapter.AppsDataViewHolder>() {

    private var recycler: StickyRecyclerView? = null

    private var items = listOf<StateableAppsGroupByCategory>()

    fun setAppsCategories(appsCategory: List<AppsGroupByCategory>, isAnimate: Boolean = false) {
        items = appsCategory
            .sortedBy { it.category?.position }
            .map { StateableAppsGroupByCategory(it) }
        notifyDataSetChanged()
        if (isAnimate) {
            recycler?.scheduleLayoutAnimation()
        }
    }

    private fun getState(categoryId: String?): AppsDataViewState? =
        items.find { it.data.category?.id == categoryId }?.state

    fun collapseAll() {
        items.forEach { it.state.clear() }
        notifyDataSetChanged()
        recycler?.scrollToPosition(0)
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        if (recycler is StickyRecyclerView) {
            this.recycler = recycler
        }
    }

    override fun getItemCount() = items.size

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): AppsHeaderViewHolder =
        AppsHeaderViewHolder(AppsHeaderView(parent.context))

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): AppsDataViewHolder =
        AppsDataViewHolder(recycler, AppsDataView(parent.context))

    override fun onBindViewHolder(
        parent: ViewGroup,
        headerHolder: AppsHeaderViewHolder,
        contentHolder: AppsDataViewHolder,
        position: Int
    ) {
        val item = items[position]
        saveState(headerHolder, contentHolder)
        headerHolder.bind(item)
        contentHolder.bind(item)
        val header = headerHolder.view
        header.onExpandedListener = HeaderExpandedListener(recycler, parent, contentHolder.view)
        restoreState(headerHolder, contentHolder)
    }

    private fun saveState(headerHolder: AppsHeaderViewHolder, contentHolder: AppsDataViewHolder) {
        val categoryId = headerHolder.view.categoryId ?: return
        val state = getState(categoryId)
        val content = contentHolder.view
        content.save(state)
    }

    private fun restoreState(
        headerHolder: AppsHeaderViewHolder,
        contentHolder: AppsDataViewHolder
    ) {
        val header = headerHolder.view
        val content = contentHolder.view
        val state = getState(header.categoryId)
        content.restore(state)
        header.isExpanded = content.isExpanded
    }

    class AppsHeaderViewHolder(val view: AppsHeaderView) : RecyclerView.ViewHolder(view) {

        fun bind(appCategory: StateableAppsGroupByCategory) {
            val category = appCategory.data.category ?: return
            view.setCategory(category)
        }
    }

    class AppsDataViewHolder(
        private val parent: StickyRecyclerView?,
        val view: AppsDataView
    ) : RecyclerView.ViewHolder(view) {

        fun bind(appCategory: StateableAppsGroupByCategory) {
            // hack to perform a artificial scroll after resizing StickyRecyclerView
            view.setOnAnimationCancelListener {
                if (!view.isExpanded) {
                    parent?.post { parent.scrollBy(1, 0) }
                }
            }
            view.setApps(appCategory.data.apps)
        }
    }

    private class HeaderExpandedListener(
        private val recycler: StickyRecyclerView?,
        private val parent: ViewGroup,
        private val data: AppsDataView
    ) : ArrowItemView.OnExpandedClickListener {

        override fun onExpandedClick(isExpanded: Boolean, isAnimate: Boolean) {
            when {
                isAnimate && isExpanded != data.isExpanded && parent.top < 0 -> {
                    recycler?.scrollToTopHeader(onCancel = { data.animatedExpand(isExpanded) })
                }
                isAnimate && isExpanded != data.isExpanded -> data.animatedExpand(isExpanded)
                !isAnimate -> data.isExpanded = isExpanded
            }
        }
    }
}