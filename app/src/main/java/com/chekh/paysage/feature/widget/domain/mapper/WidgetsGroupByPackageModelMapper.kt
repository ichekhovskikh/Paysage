package com.chekh.paysage.feature.widget.domain.mapper

import com.chekh.paysage.core.mapper.TwoParametersMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetAppModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByPackageModel
import javax.inject.Inject

class WidgetsGroupByPackageModelMapper @Inject constructor() :
    TwoParametersMapper<WidgetAppModel?, List<WidgetModel>, WidgetsGroupByPackageModel> {

    override fun map(
        firstSource: WidgetAppModel?,
        secondSource: List<WidgetModel>
    ) = WidgetsGroupByPackageModel(
        widgetApp = firstSource,
        widgets = secondSource
    )
}
