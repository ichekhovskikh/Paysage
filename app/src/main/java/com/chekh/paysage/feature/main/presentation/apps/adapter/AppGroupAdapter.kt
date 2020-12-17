package com.chekh.paysage.feature.main.presentation.apps.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chekh.paysage.core.ui.view.stickyheader.StickyAdapter
import com.chekh.paysage.feature.main.presentation.apps.adapter.differ.AppGroupDiffCallback
import com.chekh.paysage.feature.main.presentation.apps.adapter.holder.AppGroupDataViewHolder
import com.chekh.paysage.feature.main.presentation.apps.adapter.holder.AppGroupHeaderViewHolder
import com.chekh.paysage.feature.main.presentation.apps.adapter.payload.AppGroupAppsChanged
import com.chekh.paysage.feature.main.presentation.apps.adapter.payload.AppGroupStateChanged
import com.chekh.paysage.feature.main.presentation.apps.model.AppGroupModel
import com.chekh.paysage.feature.main.presentation.apps.view.AppGroupDataView
import com.chekh.paysage.feature.main.presentation.apps.view.AppGroupHeaderView

class AppGroupAdapter(
    private val onCategoryClick: (Int, String) -> Unit,
    private val onGroupScrollOffsetChanged: (Int, String) -> Unit
) : StickyAdapter<AppGroupModel, AppGroupHeaderViewHolder, AppGroupDataViewHolder>(
    AppGroupDiffCallback()
) {

    private val sharedPool = RecycledViewPool().apply { setMaxRecycledViews(0, SHARED_POOL_SIZE) }
    private var recycler: RecyclerView? = null

    fun setAppGroups(
        appGroups: List<AppGroupModel>,
        isAnimate: Boolean = false
    ) {
        submitList(appGroups)
        if (isAnimate) {
            recycler?.scheduleLayoutAnimation()
        }
    }

    override fun onAttachedToRecyclerView(recycler: RecyclerView) {
        super.onAttachedToRecyclerView(recycler)
        this.recycler = recycler
    }

    override fun onCreateHeaderViewHolder(parent: ViewGroup, viewType: Int): AppGroupHeaderViewHolder =
        AppGroupHeaderViewHolder(AppGroupHeaderView(parent.context), onCategoryClick)

    override fun onCreateContentViewHolder(parent: ViewGroup, viewType: Int): AppGroupDataViewHolder =
        AppGroupDataViewHolder(
            view = AppGroupDataView(parent.context).apply { setRecycledViewPool(sharedPool) },
            onScrollChanged = onGroupScrollOffsetChanged
        )

    override fun onBindHeaderViewHolder(
        parent: ViewGroup,
        headerHolder: AppGroupHeaderViewHolder,
        position: Int,
        payloads: List<Any>?
    ) {
        val item = getItem(position)
        if (payloads.isNullOrEmpty()) {
            headerHolder.bind(position, item)
            return
        }
        for (payload in payloads) {
            if (payload is AppGroupStateChanged) {
                headerHolder.expand(payload.isExpanded)
            }
        }
    }

    override fun onBindContentViewHolder(
        parent: ViewGroup,
        contentHolder: AppGroupDataViewHolder,
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
                is AppGroupStateChanged -> {
                    contentHolder.setScrollOffset(payload.scrollOffset)
                    contentHolder.expand(payload.isExpanded)
                }
                is AppGroupAppsChanged -> {
                    contentHolder.setApps(payload.apps, payload.appSettings)
                }
            }
        }
    }

    companion object {
        private const val SHARED_POOL_SIZE = 20
    }
}
