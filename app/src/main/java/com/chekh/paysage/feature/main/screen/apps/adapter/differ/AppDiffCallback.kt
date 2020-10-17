package com.chekh.paysage.feature.main.screen.apps.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.core.ui.tools.isSame
import com.chekh.paysage.feature.main.domain.model.AppModel

class AppDiffCallback : DiffUtil.ItemCallback<AppModel>() {

    override fun areItemsTheSame(oldItem: AppModel, newItem: AppModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: AppModel, newItem: AppModel) =
        oldItem.packageName == newItem.packageName &&
            oldItem.className == newItem.className &&
            oldItem.title == newItem.title &&
            oldItem.icon.isSame(newItem.icon) &&
            oldItem.categoryId == newItem.categoryId &&
            oldItem.position == newItem.position &&
            oldItem.isHidden == newItem.isHidden &&
            oldItem.iconColor == newItem.iconColor

    override fun getChangePayload(oldItem: AppModel, newItem: AppModel) = Unit
}
