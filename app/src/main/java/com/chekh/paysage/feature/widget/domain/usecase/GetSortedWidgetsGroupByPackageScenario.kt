package com.chekh.paysage.feature.widget.domain.usecase

import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.core.extension.sortedBy
import com.chekh.paysage.feature.widget.domain.mapper.WidgetsGroupByPackageModelMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByPackageModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetSortedWidgetsGroupByPackageScenario @Inject constructor(
    private val getBoardWidgetsUseCase: GetBoardWidgetsUseCase,
    private val getFirstAppForWidgetPackageUseCase: GetFirstAppForWidgetPackageUseCase,
    private val widgetsGroupByPackageMapper: WidgetsGroupByPackageModelMapper
) {

    operator fun invoke(): Flow<List<WidgetsGroupByPackageModel>> = getBoardWidgetsUseCase()
        .map { widgets -> widgets.groupBy { it.packageName }.toList() }
        .foreachMap { (packageName, widgets) ->
            val app = getFirstAppForWidgetPackageUseCase(packageName)
            val sortedWidgets = widgets.sortedBy {
                val previewImage = it.previewImage ?: return@sortedBy 1f
                previewImage.width.toFloat() / previewImage.height.toFloat()
            }
            widgetsGroupByPackageMapper.map(app, sortedWidgets)
        }
        .sortedBy { it.widgetApp?.label }
}
