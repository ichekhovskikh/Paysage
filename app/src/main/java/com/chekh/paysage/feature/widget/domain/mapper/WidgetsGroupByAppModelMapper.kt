package com.chekh.paysage.feature.widget.domain.mapper

import com.chekh.paysage.core.mapper.TwoParametersMapper
import com.chekh.paysage.feature.widget.domain.model.AppForWidgetModel
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByAppModel
import javax.inject.Inject

class WidgetsGroupByAppModelMapper @Inject constructor() :
    TwoParametersMapper<AppForWidgetModel?, List<WidgetModel>, WidgetsGroupByAppModel> {

    override fun map(
        firstSource: AppForWidgetModel?,
        secondSource: List<WidgetModel>
    ) = WidgetsGroupByAppModel(
        app = firstSource,
        widgets = secondSource
    )
}
