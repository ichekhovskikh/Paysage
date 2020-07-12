package com.chekh.paysage.feature.home.screen.apps.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.home.screen.apps.data.AppsCategoryAppsChanged
import com.chekh.paysage.feature.home.screen.apps.data.AppsCategoryStateChanged
import com.chekh.paysage.feature.home.screen.apps.model.ExpandableAppsGroupByCategoryModel

class AppsGroupByCategoryDiffCallback :
    DiffUtil.ItemCallback<ExpandableAppsGroupByCategoryModel>() {

    override fun areItemsTheSame(
        oldItem: ExpandableAppsGroupByCategoryModel,
        newItem: ExpandableAppsGroupByCategoryModel
    ): Boolean {
        return oldItem.data.category.id == newItem.data.category.id
    }

    override fun areContentsTheSame(
        oldItem: ExpandableAppsGroupByCategoryModel,
        newItem: ExpandableAppsGroupByCategoryModel
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(
        oldItem: ExpandableAppsGroupByCategoryModel,
        newItem: ExpandableAppsGroupByCategoryModel
    ): Any? = when {
        oldItem != newItem && oldItem.data == newItem.data -> {
            AppsCategoryStateChanged(newItem.isExpanded, newItem.scrollOffset)
        }
        oldItem.data.category == newItem.data.category &&
                oldItem.data.appList != newItem.data.appList -> {
            AppsCategoryAppsChanged(newItem.data.appList)
        }
        else -> {
            super.getChangePayload(oldItem, newItem)
        }
    }
}