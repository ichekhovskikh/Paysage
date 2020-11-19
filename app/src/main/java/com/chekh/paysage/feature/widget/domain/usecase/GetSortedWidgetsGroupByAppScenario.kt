package com.chekh.paysage.feature.widget.domain.usecase

import androidx.lifecycle.LiveData
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.feature.widget.domain.mapper.WidgetsGroupByAppModelMapper
import com.chekh.paysage.feature.widget.domain.model.WidgetsGroupByAppModel
import java.lang.Float.MAX_VALUE
import javax.inject.Inject

class GetSortedWidgetsGroupByAppScenario @Inject constructor(
    private val getBoardWidgetsUseCase: GetBoardWidgetsUseCase,
    private val getFirstAppForWidgetPackageUseCase: GetFirstAppForWidgetPackageUseCase,
    private val widgetsGroupByAppMapper: WidgetsGroupByAppModelMapper
) {

    operator fun invoke(): LiveData<List<WidgetsGroupByAppModel>> = getBoardWidgetsUseCase()
        .map { widgets -> widgets?.groupBy { it.packageName }?.toList() }
        .foreachMap { (packageName, widgets) ->
            getFirstAppForWidgetPackageUseCase(packageName).map { it to widgets }
        }
        .collect { (app, widgets) ->
            val sortedWidgets = widgets.sortedBy {
                val previewImage = it.previewImage ?: return@sortedBy MAX_VALUE
                previewImage.width.toFloat() / previewImage.height.toFloat()
            }
            widgetsGroupByAppMapper.map(app, sortedWidgets)
        }
        .sortedBy { it.app?.label }
}
