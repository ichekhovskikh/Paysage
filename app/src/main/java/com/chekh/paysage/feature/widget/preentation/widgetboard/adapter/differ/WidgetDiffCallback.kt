package com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.widget.domain.model.WidgetModel

class WidgetDiffCallback : DiffUtil.ItemCallback<WidgetModel>() {

    override fun areItemsTheSame(oldItem: WidgetModel, newItem: WidgetModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: WidgetModel, newItem: WidgetModel) =
        oldItem.previewImageRes == newItem.previewImageRes &&
            oldItem.minHeight != newItem.minHeight &&
            oldItem.minWidth != newItem.minWidth

    override fun getChangePayload(oldItem: WidgetModel, newItem: WidgetModel) = Unit
}
