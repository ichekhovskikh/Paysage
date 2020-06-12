package com.chekh.paysage.feature.home.screen.apps.adapter

import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chekh.paysage.R
import com.chekh.paysage.feature.home.screen.apps.adapter.differ.AppsCategoryStateChanged
import com.chekh.paysage.feature.home.screen.apps.adapter.differ.AppsGroupByCategoryDiffCallback
import com.chekh.paysage.feature.home.screen.apps.model.ExpandableAppsGroupByCategory
import com.chekh.paysage.feature.home.screen.apps.view.AppsDataView
import com.chekh.paysage.feature.home.screen.apps.view.AppsHeaderView
import com.chekh.paysage.ui.view.stickyheader.StickyAdapter

class AppsCategoryAdapter(
    private val onCategoryClick: (Int, String) -> Unit,
    private val onScrollCategoryChange: (Int, String) -> Unit
) : StickyAdapter<ExpandableAppsGroupByCategory, AppsCategoryAdapter.AppsHeaderViewHolder, AppsCategoryAdapter.AppsDataViewHolder>(
    AppsGroupByCategoryDiffCallback()
) {

    private val sharedPool = RecycledViewPool().apply { setMaxRecycledViews(0, SHARED_POOL_SIZE) }
    private var recycler: RecyclerView? = null
    private var items = listOf<ExpandableAppsGroupByCategory>()

    fun setAppsCategories(
        appsCategory: List<ExpandableAppsGroupByCategory>,
        isAnimate: Boolean = false
    ) {
        items = appsCategory
        submitList(appsCategory)
        if (isAnimate) {
            recycler?.scheduleLayoutAnimation()
        }
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        this.recycler = recycler
    }

    override fun getItemCount() = items.size

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): AppsHeaderViewHolder =
        AppsHeaderViewHolder(AppsHeaderView(parent.context), onCategoryClick)

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): AppsDataViewHolder =
        AppsDataViewHolder(
            view = AppsDataView(parent.context).apply { setRecycledViewPool(sharedPool) },
            onScrollChange = onScrollCategoryChange
        )

    override fun onBindHeaderViewHolder(
        parent: ViewGroup,
        headerHolder: AppsHeaderViewHolder,
        position: Int,
        payloads: List<Any>?
    ) {
        val item = items[position]
        if (payloads.isNullOrEmpty()) {
            headerHolder.bind(position, item)
            return
        }
        for (payload in payloads) {
            if (payload is AppsCategoryStateChanged) {
                headerHolder.expand(payload.isExpanded)
            }
        }
    }

    override fun onBindContentViewHolder(
        parent: ViewGroup,
        contentHolder: AppsDataViewHolder,
        position: Int,
        payloads: List<Any>?
    ) {
        val item = items[position]
        if (payloads.isNullOrEmpty()) {
            contentHolder.bind(item)
            return
        }
        for (payload in payloads) {
            if (payload is AppsCategoryStateChanged) {
                contentHolder.setScrollOffset(payload.scrollOffset)
                contentHolder.expand(payload.isExpanded)
            }
        }
    }

    class AppsHeaderViewHolder(
        private val view: AppsHeaderView,
        private val onCategoryClick: (Int, String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                val padding = resources.getDimension(R.dimen.small).toInt()
                val textSize = resources.getDimension(R.dimen.medium_text_size)
                setPadding(padding, padding, padding, padding)
                setTextSize(textSize, COMPLEX_UNIT_PX)
            }
        }

        fun bind(position: Int, appCategory: ExpandableAppsGroupByCategory) = with(view) {
            val category = appCategory.data.category ?: return
            setOnClickListener { onCategoryClick(position, category.id) }
            setIcon(category.title.iconRes)
            setTitle(category.title.titleRes)
            isExpanded = appCategory.isExpanded
        }

        fun expand(isExpanded: Boolean) {
            view.animatedExpand(isExpanded)
        }
    }

    class AppsDataViewHolder(
        private val view: AppsDataView,
        private val onScrollChange: (Int, String) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                val padding = resources.getDimension(R.dimen.small).toInt()
                setPadding(padding, padding, padding, padding)
            }
        }

        fun bind(appCategory: ExpandableAppsGroupByCategory) = with(view) {
            setApps(appCategory.data.apps)
            isExpanded = appCategory.isExpanded
            scrollOffset = appCategory.scrollOffset

            setOffsetChangeListener { offset ->
                val category = appCategory.data.category ?: return@setOffsetChangeListener
                onScrollChange(offset, category.id)
            }
        }

        fun expand(isExpanded: Boolean) {
            view.animatedExpand(isExpanded)
        }

        fun setScrollOffset(scrollOffset: Int) {
            view.scrollOffset = scrollOffset
        }
    }

    companion object {
        private const val SHARED_POOL_SIZE = 20
    }
}