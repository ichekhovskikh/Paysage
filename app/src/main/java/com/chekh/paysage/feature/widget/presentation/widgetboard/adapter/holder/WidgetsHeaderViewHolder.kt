package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.holder

import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.widget.presentation.widgetboard.model.ScrollableWidgetsGroupByPackageModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.view.WidgetsHeaderView

class WidgetsHeaderViewHolder(
    private val view: WidgetsHeaderView
) : RecyclerView.ViewHolder(view) {

    init {
        view.apply {
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            val verticalPadding = resources.getDimension(R.dimen.small).toInt()
            val horizontalPadding = resources.getDimension(R.dimen.medium).toInt()
            val textSize = resources.getDimension(R.dimen.medium_text_size)
            setPadding(horizontalPadding, verticalPadding, horizontalPadding, verticalPadding)
            setTextSize(textSize, COMPLEX_UNIT_PX)
        }
    }

    fun bind(widgetsGroupByPackage: ScrollableWidgetsGroupByPackageModel) = with(view) {
        icon = widgetsGroupByPackage.data.widgetApp?.icon?.toDrawable(context.resources)
        title = widgetsGroupByPackage.data.widgetApp?.label
    }
}
