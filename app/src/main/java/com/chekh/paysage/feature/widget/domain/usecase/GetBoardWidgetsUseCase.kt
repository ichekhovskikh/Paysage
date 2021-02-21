package com.chekh.paysage.feature.widget.domain.usecase

import android.util.DisplayMetrics
import com.chekh.paysage.core.extension.with
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min
import kotlin.math.round

class GetBoardWidgetsUseCase @Inject constructor(
    private val gateway: WidgetGateway,
    private val displayMetrics: DisplayMetrics
) {

    operator fun invoke() = gateway.getBoardWidgets()
        .with(gateway.getDesktopGridSize()) { widgets, (columns, rows) ->
            val columnWidth = displayMetrics.widthPixels.toFloat() / columns
            val rowWidth = displayMetrics.heightPixels.toFloat() / rows
            widgets.map {
                val minColumns = min(
                    max(1, round(it.minWidth.toFloat() / columnWidth).toInt()),
                    columns.toInt()
                )
                val minRows = min(
                    max(1, round(it.minHeight.toFloat() / rowWidth).toInt()),
                    rows.toInt()
                )
                it.copy(minColumns = minColumns, minRows = minRows)
            }
        }
}
