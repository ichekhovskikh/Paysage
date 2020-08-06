package com.chekh.paysage.feature.home.screen.apps.adapter

import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chekh.paysage.R
import com.chekh.paysage.feature.home.domain.model.AppListModel
import com.chekh.paysage.feature.home.screen.apps.data.AppsCategoryStateChanged
import com.chekh.paysage.feature.home.screen.apps.adapter.differ.AppsGroupByCategoryDiffCallback
import com.chekh.paysage.feature.home.screen.apps.data.AppsCategoryAppsChanged
import com.chekh.paysage.feature.home.screen.apps.model.ExpandableAppsGroupByCategoryModel
import com.chekh.paysage.feature.home.screen.apps.view.AppsDataView
import com.chekh.paysage.feature.home.screen.apps.view.AppsHeaderView
import com.chekh.paysage.ui.view.stickyheader.StickyAdapter

class AppsCategoryAdapter(
    private val onCategoryClick: (Int, String) -> Unit,
    private val onScrollCategoryChange: (Int, String) -> Unit,
    private val onScrollStateChange: (Int) -> Unit
) : StickyAdapter<ExpandableAppsGroupByCategoryModel, AppsCategoryAdapter.AppsHeaderViewHolder, AppsCategoryAdapter.AppsDataViewHolder>(
    AppsGroupByCategoryDiffCallback()
) {

    private val sharedPool = RecycledViewPool().apply { setMaxRecycledViews(0, SHARED_POOL_SIZE) }
    private var recycler: RecyclerView? = null
    private var items = listOf<ExpandableAppsGroupByCategoryModel>()

    fun setAppsCategories(
        appsCategory: List<ExpandableAppsGroupByCategoryModel>,
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

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): AppsHeaderViewHolder =
        AppsHeaderViewHolder(AppsHeaderView(parent.context), onCategoryClick)

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): AppsDataViewHolder =
        AppsDataViewHolder(
            view = AppsDataView(parent.context).apply { setRecycledViewPool(sharedPool) },
            onScrollChange = onScrollCategoryChange,
            onScrollStateChange = onScrollStateChange
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
            when (payload) {
                is AppsCategoryStateChanged -> {
                    contentHolder.setScrollOffset(payload.scrollOffset)
                    contentHolder.expand(payload.isExpanded)
                }
                is AppsCategoryAppsChanged -> {
                    contentHolder.setApps(payload.apps)
                }
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

        fun bind(position: Int, appCategory: ExpandableAppsGroupByCategoryModel) = with(view) {
            val category = appCategory.data.category
            setOnClickListener { onCategoryClick(position, category.id) }
            setIcon(category.category.iconRes)
            setTitle(category.category.titleRes)
            isExpanded = appCategory.isExpanded
        }

        fun expand(isExpanded: Boolean) {
            view.animatedExpand(isExpanded)
        }
    }

    class AppsDataViewHolder(
        private val view: AppsDataView,
        private val onScrollChange: (Int, String) -> Unit,
        private val onScrollStateChange: (Int) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        init {
            view.apply {
                layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
                val padding = resources.getDimension(R.dimen.small).toInt()
                setPadding(padding, padding, padding, padding)
                setScrollStateChangeListener { onScrollStateChange(it) }
            }
        }

        fun bind(appCategory: ExpandableAppsGroupByCategoryModel) {
            setApps(appCategory.data.appList)
            view.isExpanded = appCategory.isExpanded
            view.scrollOffset = appCategory.scrollOffset

            view.setOffsetChangeListener { offset ->
                val category = appCategory.data.category
                onScrollChange(offset, category.id)
            }
        }

        fun setApps(appList: AppListModel) {
            view.spanCount = appList.appSpan
            view.appSize = appList.appSize
            view.setApps(appList.apps)
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