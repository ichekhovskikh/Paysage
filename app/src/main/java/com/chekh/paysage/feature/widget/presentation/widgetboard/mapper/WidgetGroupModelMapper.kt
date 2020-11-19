package com.chekh.paysage.feature.widget.presentation.widgetboard.mapper

import com.chekh.paysage.core.mapper.TwoParametersMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByAppModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.model.WidgetGroupModel
import javax.inject.Inject

class WidgetGroupModelMapper @Inject constructor() :
    TwoParametersMapper<WidgetsGroupByAppModel, Int, WidgetGroupModel> {

    override fun map(
        firstSource: WidgetsGroupByAppModel,
        secondSource: Int
    ) = WidgetGroupModel(
        data = firstSource,
        scrollOffset = secondSource
    )
}
