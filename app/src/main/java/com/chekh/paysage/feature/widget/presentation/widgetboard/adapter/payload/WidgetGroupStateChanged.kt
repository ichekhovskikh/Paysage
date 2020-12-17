package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.payload

import com.chekh.paysage.feature.widget.presentation.widgetboard.model.WidgetGroupModel

data class WidgetGroupStateChanged(val scrollOffset: Int)

fun isWidgetGroupStateChanged(
    oldItem: WidgetGroupModel,
    newItem: WidgetGroupModel
) = oldItem.scrollOffset != newItem.scrollOffset
