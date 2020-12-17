package com.chekh.paysage.feature.main.presentation.apps.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.main.presentation.apps.adapter.payload.AppGroupAppsChanged
import com.chekh.paysage.feature.main.presentation.apps.adapter.payload.AppGroupStateChanged
import com.chekh.paysage.feature.main.presentation.apps.adapter.payload.isAppGroupAppsChanged
import com.chekh.paysage.feature.main.presentation.apps.adapter.payload.isAppGroupStateChanged
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
        isAppGroupStateChanged(oldItem, newItem) -> {
            AppGroupStateChanged(newItem.isExpanded, newItem.scrollOffset)
        }
        isAppGroupAppsChanged(oldItem, newItem) -> {
            AppGroupAppsChanged(newItem.data.apps, newItem.data.settings)
        }
        else -> super.getChangePayload(oldItem, newItem)
    }
}
