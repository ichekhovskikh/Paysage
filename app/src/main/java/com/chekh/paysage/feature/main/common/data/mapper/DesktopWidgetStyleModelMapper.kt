package com.chekh.paysage.feature.main.common.data.mapper

import com.chekh.paysage.common.data.model.DesktopWidgetStyleSettingsEntity
import com.chekh.paysage.core.mapper.BidirectionalOneParameterMapper
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetStyleModel
import javax.inject.Inject

class DesktopWidgetStyleModelMapper @Inject constructor() :
    BidirectionalOneParameterMapper<DesktopWidgetStyleSettingsEntity?, DesktopWidgetStyleModel?> {

    override fun map(source: DesktopWidgetStyleSettingsEntity?) = source?.run {
        DesktopWidgetStyleModel(
            color = color,
            alpha = alpha,
            corner = corner,
            elevation = elevation
        )
    }

    override fun unmap(source: DesktopWidgetStyleModel?) = source?.run {
        DesktopWidgetStyleSettingsEntity(
            color = color,
            alpha = alpha,
            corner = corner,
            elevation = elevation
        )
    }
}
