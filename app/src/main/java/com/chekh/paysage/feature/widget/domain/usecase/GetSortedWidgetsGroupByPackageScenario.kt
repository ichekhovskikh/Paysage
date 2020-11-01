package com.chekh.paysage.feature.widget.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.collect
import com.chekh.paysage.core.extension.foreachMap
import com.chekh.paysage.core.extension.map
import com.chekh.paysage.core.extension.sortedBy
import com.chekh.paysage.feature.widget.domain.mapper.WidgetsGroupByPackageModelMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByPackageModel
import javax.inject.Inject

class GetSortedWidgetsGroupByPackageScenario @Inject constructor(
    private val getBoardWidgetsUseCase: GetBoardWidgetsUseCase,
    private val getFirstWidgetAppByPackageUseCase: GetFirstWidgetAppByPackageUseCase,
    private val widgetsGroupByPackageMapper: WidgetsGroupByPackageModelMapper
) {

    operator fun invoke(): LiveData<List<WidgetsGroupByPackageModel>> = getBoardWidgetsUseCase()
        .map { widgets -> widgets?.groupBy { it.packageName }?.toList() }
        .foreachMap { (packageName, widgets) ->
            getFirstWidgetAppByPackageUseCase(packageName).map { it to widgets }
        }
        .collect { (app, widgets) ->
            val sortedWidgets = widgets.sortedBy {
                val previewImage = it.previewImage ?: return@sortedBy 1f
                previewImage.width.toFloat() / previewImage.height.toFloat()
            }
            widgetsGroupByPackageMapper.map(app, sortedWidgets)
        }
        .sortedBy { it.widgetApp?.label }
}
