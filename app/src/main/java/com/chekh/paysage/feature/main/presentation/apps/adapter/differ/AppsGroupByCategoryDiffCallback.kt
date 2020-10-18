package com.chekh.paysage.feature.main.presentation.apps.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.main.presentation.apps.data.AppsCategoryAppsChanged
import com.chekh.paysage.feature.main.presentation.apps.data.AppsCategoryStateChanged
import com.chekh.paysage.feature.main.presentation.apps.model.ExpandableAppsGroupByCategoryModel

class AppsGroupByCategoryDiffCallback :
    DiffUtil.ItemCallback<ExpandableAppsGroupByCategoryModel>() {

    override fun areItemsTheSame(
        oldItem: ExpandableAppsGroupByCategoryModel,
        newItem: ExpandableAppsGroupByCategoryModel
    ) = oldItem.data.category.id == newItem.data.category.id

    override fun areContentsTheSame(
        oldItem: ExpandableAppsGroupByCategoryModel,
        newItem: ExpandableAppsGroupByCategoryModel
    ) = oldItem == newItem

    override fun getChangePayload(
        oldItem: ExpandableAppsGroupByCategoryModel,
        newItem: ExpandableAppsGroupByCategoryModel
    ) = when {
        oldItem.isExpanded != newItem.isExpanded || oldItem.scrollOffset != newItem.scrollOffset -> {
            AppsCategoryStateChanged(newItem.isExpanded, newItem.scrollOffset)
        }
        oldItem.data.category == newItem.data.category -> {
            AppsCategoryAppsChanged(newItem.data.apps, newItem.data.settings)
        }
        else -> super.getChangePayload(oldItem, newItem)
    }
}
