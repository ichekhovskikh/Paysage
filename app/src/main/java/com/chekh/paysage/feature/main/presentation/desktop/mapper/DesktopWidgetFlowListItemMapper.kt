package com.chekh.paysage.feature.main.presentation.desktop.mapper

import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.presentation.desktop.adapter.DesktopWidgetFlowListItem
import javax.inject.Inject

class DesktopWidgetFlowListItemMapper @Inject constructor() :
    OneParameterMapper<DesktopWidgetModel, DesktopWidgetFlowListItem> {

    override fun map(source: DesktopWidgetModel) = DesktopWidgetFlowListItem(
        desktopWidget = source
    )
}
