package com.chekh.paysage.feature.widget.domain.usecase

import android.util.DisplayMetrics
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.zip
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

class GetBoardWidgetsUseCase @Inject constructor(
    private val gateway: WidgetGateway,
    private val displayMetrics: DisplayMetrics
) {

    operator fun invoke(): Flow<List<WidgetModel>> = gateway.getBoardWidgets()
        .zip(gateway.getDesktopGridSpan()) { widgets, gridSpan ->
            val spanWidth = displayMetrics.densityDpi.toFloat() / gridSpan
            widgets.map {
                val minColumns = min(ceil(it.minWidth.toFloat() / spanWidth).toInt(), gridSpan)
                val minRows = ceil(it.minHeight.toFloat() / it.minWidth * minColumns).toInt()
                it.copy(minColumns = minColumns, minRows = minRows)
            }
        }
}
