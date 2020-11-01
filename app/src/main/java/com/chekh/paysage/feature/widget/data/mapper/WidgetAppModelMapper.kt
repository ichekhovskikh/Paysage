package com.chekh.paysage.feature.widget.data.mapper

import com.chekh.paysage.common.data.model.AppSettingsEntity
import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetAppModel
import javax.inject.Inject

class WidgetAppModelMapper @Inject constructor() :
    OneParameterMapper<AppSettingsEntity, WidgetAppModel> {

    override fun map(source: AppSettingsEntity) = WidgetAppModel(
        id = source.id,
        icon = source.icon,
        label = source.title
    )
}
