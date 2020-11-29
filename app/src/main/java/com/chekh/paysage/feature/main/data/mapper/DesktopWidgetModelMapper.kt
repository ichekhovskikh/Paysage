package com.chekh.paysage.feature.main.data.mapper

import com.chekh.paysage.common.data.model.DesktopWidgetSettingsEntity
import com.chekh.paysage.core.mapper.BidirectionalOneParameterMapper
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
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
        x = source.x,
        y = source.y,
        height = source.height,
        width = source.width,
        minHeight = source.minHeight,
        minWidth = source.minWidth,
        style = desktopWidgetStyleMapper.map(source.style)
    )

    override fun unmap(source: DesktopWidgetModel) = DesktopWidgetSettingsEntity(
        id = source.id,
        packageName = source.packageName,
        className = source.className,
        label = source.label,
        type = source.type,
        x = source.x,
        y = source.y,
        height = source.height,
        width = source.width,
        minHeight = source.minHeight,
        minWidth = source.minWidth,
        style = desktopWidgetStyleMapper.unmap(source.style)
    )
}
