package com.chekh.paysage.feature.widget.data.mapper

import com.chekh.paysage.common.data.model.AppSettingsEntity
import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.feature.widget.domain.model.AppForWidgetModel
import javax.inject.Inject

class AppForWidgetModelMapper @Inject constructor() :
    OneParameterMapper<AppSettingsEntity, AppForWidgetModel> {

    override fun map(source: AppSettingsEntity) = AppForWidgetModel(
        id = source.id,
        icon = source.icon,
        label = source.title
    )
}
