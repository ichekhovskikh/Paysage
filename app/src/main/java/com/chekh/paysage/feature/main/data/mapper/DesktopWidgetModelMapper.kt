package com.chekh.paysage.feature.main.data.mapper

import com.chekh.paysage.common.data.model.DesktopWidgetSettingsEntity
import com.chekh.paysage.core.mapper.BidirectionalOneParameterMapper
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetStyleModel
import javax.inject.Inject

class DesktopWidgetModelMapper @Inject constructor(
    private val desktopWidgetStyleMapper: DesktopWidgetStyleModelMapper
) : BidirectionalOneParameterMapper<DesktopWidgetSettingsEntity, DesktopWidgetModel> {

    override fun map(source: DesktopWidgetSettingsEntity) = DesktopWidgetModel(
        id = source.id,
        packageName = source.packageName,
        className = source.className,
        label = source.label,
        type = source.type,
        bounds = source.bounds,
        page = source.page,
        minHeight = source.minHeight,
        minWidth = source.minWidth,
        style = desktopWidgetStyleMapper.map(source.style) ?: DesktopWidgetStyleModel.EMPTY
    )

    override fun unmap(source: DesktopWidgetModel) = DesktopWidgetSettingsEntity(
        id = source.id,
        packageName = source.packageName,
        className = source.className,
        label = source.label,
        type = source.type,
        bounds = source.bounds,
        page = source.page,
        minHeight = source.minHeight,
        minWidth = source.minWidth,
        style = desktopWidgetStyleMapper.unmap(source.style)
    )
}
