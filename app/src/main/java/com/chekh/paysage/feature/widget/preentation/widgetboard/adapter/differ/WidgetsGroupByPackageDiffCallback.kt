package com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.differ

import androidx.recyclerview.widget.DiffUtil
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.data.WidgetsPackageStateChanged
import com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.data.WidgetsPackageWidgetsChanged
import com.chekh.paysage.feature.widget.preentation.widgetboard.model.ScrollableWidgetsGroupByPackageModel

class WidgetsGroupByPackageDiffCallback :
    DiffUtil.ItemCallback<ScrollableWidgetsGroupByPackageModel>() {

    override fun areItemsTheSame(
        oldItem: ScrollableWidgetsGroupByPackageModel,
        newItem: ScrollableWidgetsGroupByPackageModel
    ) = oldItem.data.widgetApp?.id == newItem.data.widgetApp?.id

    override fun areContentsTheSame(
        oldItem: ScrollableWidgetsGroupByPackageModel,
        newItem: ScrollableWidgetsGroupByPackageModel
    ) = oldItem == newItem

    override fun getChangePayload(
        oldItem: ScrollableWidgetsGroupByPackageModel,
        newItem: ScrollableWidgetsGroupByPackageModel
    ) = when {
        oldItem.scrollOffset != newItem.scrollOffset -> {
            WidgetsPackageStateChanged(newItem.scrollOffset)
        }
        oldItem.data.widgets == newItem.data.widgets -> {
            WidgetsPackageWidgetsChanged(newItem.data.widgets)
        }
        else -> super.getChangePayload(oldItem, newItem)
    }
}
