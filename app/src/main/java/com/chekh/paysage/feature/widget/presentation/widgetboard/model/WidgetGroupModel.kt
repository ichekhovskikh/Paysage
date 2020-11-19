package com.chekh.paysage.feature.widget.presentation.widgetboard.model

import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByAppModel

data class WidgetGroupModel(
    val data: WidgetsGroupByAppModel,
    val scrollOffset: Int = 0
)
