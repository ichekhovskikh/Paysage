package com.chekh.paysage.feature.home.apps.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.home.apps.model.ExpandableAppsGroupByCategory

class AppsGroupByCategoryDiffCallback : DiffUtil.ItemCallback<ExpandableAppsGroupByCategory>() {

    override fun areItemsTheSame(
        oldItem: ExpandableAppsGroupByCategory,
        newItem: ExpandableAppsGroupByCategory
    ): Boolean {
        return oldItem.data.category?.id == newItem.data.category?.id
    }

    override fun areContentsTheSame(
        oldItem: ExpandableAppsGroupByCategory,
        newItem: ExpandableAppsGroupByCategory
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: ExpandableAppsGroupByCategory,
        newItem: ExpandableAppsGroupByCategory
    ): Any? {
        if (oldItem != newItem && oldItem.data == newItem.data) {
            return AppsCategoryStateChanged(newItem.isExpanded, newItem.scrollOffset)
        }
        return super.getChangePayload(oldItem, newItem)
    }
}