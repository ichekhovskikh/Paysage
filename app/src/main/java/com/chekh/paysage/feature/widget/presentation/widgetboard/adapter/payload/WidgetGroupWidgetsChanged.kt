package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.payload

import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.model.WidgetGroupModel

data class WidgetGroupWidgetsChanged(val widgets: List<WidgetModel>)

fun isWidgetGroupWidgetsChanged(
    oldItem: WidgetGroupModel,
    newItem: WidgetGroupModel
) = oldItem.data.widgets == newItem.data.widgets
