package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.holder

import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.widget.presentation.widgetboard.model.WidgetGroupModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.view.WidgetGroupHeaderView

class WidgetGroupHeaderViewHolder(
    private val view: WidgetGroupHeaderView
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

    fun bind(widgetsGroup: WidgetGroupModel) = with(view) {
        icon = widgetsGroup.data.app?.icon?.toDrawable(context.resources)
        title = widgetsGroup.data.app?.label
    }
}
