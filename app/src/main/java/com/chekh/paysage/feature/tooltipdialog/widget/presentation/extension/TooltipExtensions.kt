package com.chekh.paysage.feature.tooltipdialog.widget.presentation.extension

import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.chekh.paysage.feature.tooltipdialog.widget.presentation.WidgetTooltipFragment
import com.chekh.paysage.feature.tooltipdialog.widget.presentation.model.WidgetTooltipItem

private val WIDGET_TOOLTIP_REQUEST_KEY = WidgetTooltipFragment::class.java.simpleName
private val WIDGET_TOOLTIP_ITEM_KEY = WidgetTooltipItem::class.java.simpleName

fun Fragment.setWidgetTooltipResultListener(listener: (WidgetTooltipItem) -> Unit) {
    childFragmentManager.setFragmentResultListener(
        WIDGET_TOOLTIP_REQUEST_KEY,
        this
    ) { _, result ->
        result.getParcelable<WidgetTooltipItem>(WIDGET_TOOLTIP_ITEM_KEY)?.let(listener)
    }
}

internal fun Fragment.setWidgetTooltipResult(result: WidgetTooltipItem) {
    val bundle = bundleOf(WIDGET_TOOLTIP_ITEM_KEY to result)
    setFragmentResult(WIDGET_TOOLTIP_REQUEST_KEY, bundle)
}
