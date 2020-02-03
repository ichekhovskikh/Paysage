package com.chekh.paysage.feature.home.apps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.feature.home.apps.model.AppsGroupByCategory
import com.chekh.paysage.feature.home.apps.view.AppsDataView
import com.chekh.paysage.feature.home.apps.view.AppsHeaderView
import com.chekh.paysage.ui.view.ArrowItemView
import com.chekh.paysage.ui.view.stickyheader.StickyAdapter
import com.chekh.paysage.ui.view.stickyheader.StickyRecyclerView

class AppsCategoryAdapter(
    private val recycler: StickyRecyclerView
) : StickyAdapter<AppsCategoryAdapter.AppsHeaderViewHolder, AppsCategoryAdapter.AppsDataViewHolder>() {

    private var items = listOf<AppsGroupByCategoryWrapper>()

    fun setAppsCategories(appsCategory: List<AppsGroupByCategory>, isAnimate: Boolean = false) {
        items = appsCategory
            .sortedBy { it.category.position }
            .map {
                AppsGroupByCategoryWrapper(
                    it
                )
            }
        notifyDataSetChanged()
        if (isAnimate) {
            recycler.scheduleLayoutAnimation()
        }
    }

    fun collapseAll() {
        items.forEach { it.clearState() }
        notifyDataSetChanged()
        recycler.scrollToPosition(0)
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): AppsHeaderViewHolder =
        AppsHeaderViewHolder(
            AppsHeaderView(parent.context)
        )

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): AppsDataViewHolder =
        AppsDataViewHolder(
            AppsDataView(parent.context),
            recycler::onExpandedItemStateChanged
        )

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
        headerArrow.onExpandedListener = headerArrow.onExpandedListener ?: HeaderExpandedListener(
            recycler,
            parent,
            contentHolder.view
        )
        restoreState(headerHolder, contentHolder)
    }

    private fun saveState(headerHolder: AppsHeaderViewHolder, contentHolder: AppsDataViewHolder) {
        headerHolder.view.categoryId?.let { categoryId ->
            items
                .find { it.data.category.id == categoryId }
                ?.let {
                    val content = contentHolder.view
                    it.isExpanded = content.isExpanded
                    it.scrollX = content.appsScrollX
                }
        }
    }

    private fun restoreState(
        headerHolder: AppsHeaderViewHolder,
        contentHolder: AppsDataViewHolder
    ) {
        val header = headerHolder.view
        val content = contentHolder.view
        val saved = items.find { it.data.category.id == header.categoryId }
        val expanded = saved?.isExpanded ?: false
        val scrollX = saved?.scrollX ?: 0
        header.arrowItemView.nonAnimationExpand(expanded)
        content.expand(expanded)
        content.appsScrollX = scrollX
    }

    override fun getItemCount() = items.size

    class AppsHeaderViewHolder(val view: AppsHeaderView) : RecyclerView.ViewHolder(view) {

        fun bind(appCategory: AppsGroupByCategoryWrapper) {
            view.setCategory(appCategory.data.category)
        }
    }

    class AppsDataViewHolder(
        val view: AppsDataView,
        private val onExpandCancelListener: (isExpanded: Boolean) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(appCategory: AppsGroupByCategoryWrapper) {
            view.onExpandCancelListener = onExpandCancelListener
            view.setApps(appCategory.data.apps)

        }
    }

    data class AppsGroupByCategoryWrapper(
        val data: AppsGroupByCategory,
        var isExpanded: Boolean = false,
        var scrollX: Int = 0
    ) {
        fun clearState() {
            isExpanded = false
            scrollX = 0
        }
    }

    private class HeaderExpandedListener(
        private val recycler: StickyRecyclerView,
        var parent: ViewGroup,
        var data: AppsDataView
    ) : ArrowItemView.OnExpandedClickListener {

        override fun onAnimateExpandedClick(isExpanded: Boolean) {
            if (isExpanded != data.isExpanded) {
                if (parent.top < 0) {
                    recycler.scrollToTopHeader {
                        data.animationExpand(isExpanded)
                    }
                } else {
                    data.animationExpand(isExpanded)
                }
            }
        }

        override fun onExpandedClick(isExpanded: Boolean) {
            data.expand(isExpanded)
        }
    }
}