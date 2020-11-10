package com.chekh.paysage.feature.widget.presentation.widgetboard.model

import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByPackageModel

data class ScrollableWidgetsGroupByPackageModel(
    val data: WidgetsGroupByPackageModel,
    val scrollOffset: Int = 0
)
