package com.chekh.paysage.feature.main.presentation.apps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chekh.paysage.core.ui.view.stickyheader.StickyAdapter
import com.chekh.paysage.feature.main.presentation.apps.adapter.differ.AppsGroupByCategoryDiffCallback
import com.chekh.paysage.feature.main.presentation.apps.adapter.holder.AppsDataViewHolder
import com.chekh.paysage.feature.main.presentation.apps.adapter.holder.AppsHeaderViewHolder
import com.chekh.paysage.feature.main.presentation.apps.data.AppsCategoryAppsChanged
import com.chekh.paysage.feature.main.presentation.apps.data.AppsCategoryStateChanged
import com.chekh.paysage.feature.main.presentation.apps.model.ExpandableAppsGroupByCategoryModel
import com.chekh.paysage.feature.main.presentation.apps.view.AppsDataView
import com.chekh.paysage.feature.main.presentation.apps.view.AppsHeaderView

class AppsCategoryAdapter(
    private val onCategoryClick: (Int, String) -> Unit,
    private val onScrollCategoryChange: (Int, String) -> Unit
) : StickyAdapter<ExpandableAppsGroupByCategoryModel, AppsHeaderViewHolder, AppsDataViewHolder>(
    AppsGroupByCategoryDiffCallback()
) {

    private val sharedPool = RecycledViewPool().apply { setMaxRecycledViews(0, SHARED_POOL_SIZE) }
    private var recycler: RecyclerView? = null

    fun setAppsCategories(
        appsCategory: List<ExpandableAppsGroupByCategoryModel>,
        isAnimate: Boolean = false
    ) {
        submitList(appsCategory)
        if (isAnimate) {
            recycler?.scheduleLayoutAnimation()
        }
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        super.onAttachedToRecyclerView(recycler)
        this.recycler = recycler
    }

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
        val item = getItem(position)
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
        val item = getItem(position)
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
                    contentHolder.setApps(payload.apps, payload.appSettings)
                }
            }
        }
    }

    companion object {
        private const val SHARED_POOL_SIZE = 20
    }
}
