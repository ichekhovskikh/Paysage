package com.chekh.paysage.feature.widget.domain.usecase

import android.util.DisplayMetrics
import com.chekh.paysage.core.extension.with
import com.chekh.paysage.feature.widget.domain.gateway.WidgetGateway
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

class GetBoardWidgetsUseCase @Inject constructor(
    private val gateway: WidgetGateway,
    private val displayMetrics: DisplayMetrics
) {

    operator fun invoke() = gateway.getBoardWidgets()
        .with(gateway.getDesktopGridSpan()) { widgets, gridSpan ->
            val spanWidth = displayMetrics.densityDpi.toFloat() / gridSpan
            widgets.map {
                val minColumns = min(ceil(it.minWidth.toFloat() / spanWidth).toInt(), gridSpan)
                val minRows = ceil(it.minHeight.toFloat() / it.minWidth * minColumns).toInt()
                it.copy(minColumns = minColumns, minRows = minRows)
            }
        }
}
