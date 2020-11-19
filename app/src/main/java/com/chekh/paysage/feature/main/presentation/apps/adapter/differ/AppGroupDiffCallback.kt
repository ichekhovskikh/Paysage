package com.chekh.paysage.feature.main.presentation.apps.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.main.presentation.apps.data.AppGroupAppsChanged
import com.chekh.paysage.feature.main.presentation.apps.data.AppGroupStateChanged
import com.chekh.paysage.feature.main.presentation.apps.model.AppGroupModel

class AppGroupDiffCallback :
    DiffUtil.ItemCallback<AppGroupModel>() {

    override fun areItemsTheSame(
        oldItem: AppGroupModel,
        newItem: AppGroupModel
    ) = oldItem.data.category.id == newItem.data.category.id

    override fun areContentsTheSame(
        oldItem: AppGroupModel,
        newItem: AppGroupModel
    ) = oldItem == newItem

    override fun getChangePayload(
        oldItem: AppGroupModel,
        newItem: AppGroupModel
    ) = when {
        oldItem.isExpanded != newItem.isExpanded || oldItem.scrollOffset != newItem.scrollOffset -> {
            AppGroupStateChanged(newItem.isExpanded, newItem.scrollOffset)
        }
        oldItem.data.category == newItem.data.category -> {
            AppGroupAppsChanged(newItem.data.apps, newItem.data.settings)
        }
        else -> super.getChangePayload(oldItem, newItem)
    }
}
