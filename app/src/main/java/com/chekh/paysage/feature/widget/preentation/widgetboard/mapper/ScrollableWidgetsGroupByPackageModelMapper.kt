package com.chekh.paysage.feature.widget.preentation.widgetboard.mapper

import com.chekh.paysage.core.mapper.TwoParametersMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByPackageModel
import com.chekh.paysage.feature.widget.preentation.widgetboard.model.ScrollableWidgetsGroupByPackageModel
import javax.inject.Inject

class ScrollableWidgetsGroupByPackageModelMapper @Inject constructor() :
    TwoParametersMapper<WidgetsGroupByPackageModel, Int, ScrollableWidgetsGroupByPackageModel> {

    override fun map(
        firstSource: WidgetsGroupByPackageModel,
        secondSource: Int
    ) = ScrollableWidgetsGroupByPackageModel(
        data = firstSource,
        scrollOffset = secondSource
    )
}
