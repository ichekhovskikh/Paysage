package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.data.WidgetGroupStateChanged
import com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.data.WidgetGroupWidgetsChanged
import com.chekh.paysage.feature.widget.presentation.widgetboard.model.WidgetGroupModel

class WidgetsGroupDiffCallback :
    DiffUtil.ItemCallback<WidgetGroupModel>() {

    override fun areItemsTheSame(
        oldItem: WidgetGroupModel,
        newItem: WidgetGroupModel
    ) = oldItem.data.app?.id == newItem.data.app?.id

    override fun areContentsTheSame(
        oldItem: WidgetGroupModel,
        newItem: WidgetGroupModel
    ) = oldItem == newItem

    override fun getChangePayload(
        oldItem: WidgetGroupModel,
        newItem: WidgetGroupModel
    ) = when {
        oldItem.scrollOffset != newItem.scrollOffset -> {
            WidgetGroupStateChanged(newItem.scrollOffset)
        }
        oldItem.data.widgets == newItem.data.widgets -> {
            WidgetGroupWidgetsChanged(newItem.data.widgets)
        }
        else -> super.getChangePayload(oldItem, newItem)
    }
}
