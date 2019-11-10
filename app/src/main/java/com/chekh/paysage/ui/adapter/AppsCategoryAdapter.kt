package com.chekh.paysage.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.model.AppsGroupByCategory
import com.chekh.paysage.ui.view.app.AppsDataView
import com.chekh.paysage.ui.view.app.AppsHeaderView
import com.chekh.paysage.ui.view.core.ArrowItemView
import com.chekh.paysage.ui.view.core.stickyheader.StickyAdapter

class AppsCategoryAdapter :
    StickyAdapter<AppsCategoryAdapter.AppsHeaderViewHolder, AppsCategoryAdapter.AppsDataViewHolder>() {

    private var scrollToTopHeaderAction: (() -> Unit)? = null
    private var animationItemChangedAction: (() -> Unit)? = null

    private val savedCategoryStates = mutableMapOf<String, CategoryState>()
    private var items = listOf<AppsGroupByCategory>()

    fun setAppsCategories(appsCategory: List<AppsGroupByCategory>, isAnimate: Boolean = false) {
        items = appsCategory.sortedBy { it.category.position }
        savedCategoryStates.clear()
        notifyDataSetChanged()
        if (isAnimate) {
            animationItemChangedAction?.invoke()
        }
    }

    fun collapseAll() {
        savedCategoryStates.clear()
        notifyDataSetChanged()
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): AppsHeaderViewHolder {
        return AppsHeaderViewHolder(
            AppsHeaderView(parent.context)
        )
    }

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): AppsDataViewHolder {
        return AppsDataViewHolder(
            AppsDataView(parent.context)
        )
    }

    override fun onBind(
        parent: ViewGroup,
        headerHolder: AppsHeaderViewHolder,
        contentHolder: AppsDataViewHolder,
        position: Int
    ) {
        val item = items[position]
        saveState(headerHolder, contentHolder)
        headerHolder.bind(item)
        contentHolder.bind(item)
        val headerArrow = headerHolder.view.arrowItemView
        if (headerArrow.onExpandedListener == null) {
            headerArrow.onExpandedListener = HeaderExpandedListener(parent, contentHolder.view)
        }
        restoreState(headerHolder, contentHolder)
    }

    private fun saveState(headerHolder: AppsHeaderViewHolder, contentHolder: AppsDataViewHolder) {
        headerHolder.view.categoryId?.let {
            val content = contentHolder.view
            savedCategoryStates[it] =
                CategoryState(
                    content.isExpanded,
                    content.appsScrollX
                )
        }
    }

    private fun restoreState(
        headerHolder: AppsHeaderViewHolder,
        contentHolder: AppsDataViewHolder
    ) {
        val header = headerHolder.view
        val content = contentHolder.view
        val saved = savedCategoryStates[header.categoryId]
        val expanded = saved?.isExpanded ?: false
        val scrollX = saved?.scrollX ?: 0
        header.arrowItemView.nonAnimationExpand(expanded)
        content.expand(expanded)
        content.appsScrollX = scrollX
    }

    override fun getItemCount() = items.size

    fun setScrollToTopHeaderAction(action: () -> Unit) {
        scrollToTopHeaderAction = action
    }

    fun setAnimationItemChangedAction(action: () -> Unit) {
        animationItemChangedAction = action
    }

    class AppsHeaderViewHolder(val view: AppsHeaderView) : RecyclerView.ViewHolder(view) {

        fun bind(appCategory: AppsGroupByCategory) {
            view.setCategory(appCategory.category)
        }
    }

    class AppsDataViewHolder(val view: AppsDataView) : RecyclerView.ViewHolder(view) {

        fun bind(appCategory: AppsGroupByCategory) {
            view.setApps(appCategory.apps)
        }
    }

    private data class CategoryState(var isExpanded: Boolean, var scrollX: Int)

    private inner class HeaderExpandedListener(
        var parent: ViewGroup,
        var data: AppsDataView
    ) : ArrowItemView.OnExpandedClickListener {

        override fun onAnimateExpandedClick(isExpanded: Boolean) {
            if (isExpanded != data.isExpanded) {
                if (parent.top < 0) {
                    scrollToTopHeaderAction?.invoke()
                }
                data.animationExpand(isExpanded)
            }
        }

        override fun onExpandedClick(isExpanded: Boolean) {
            data.expand(isExpanded)
        }
    }
}