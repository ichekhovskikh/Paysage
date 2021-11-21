package com.chekh.paysage.feature.main.desktop.presentation.mapper

import com.chekh.paysage.core.mapper.OneParameterMapper
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.desktop.presentation.adapter.DesktopWidgetFlowListItem
import com.chekh.paysage.feature.main.desktop.presentation.tools.DesktopWidgetHostManager
import javax.inject.Inject

class DesktopWidgetFlowListItemMapper @Inject constructor(
    private val widgetHostManager: DesktopWidgetHostManager
) : OneParameterMapper<DesktopWidgetModel, DesktopWidgetFlowListItem> {

    override fun map(source: DesktopWidgetModel) = DesktopWidgetFlowListItem(
        widgetHostManager = widgetHostManager,
        desktopWidget = source
    )
}
