package com.chekh.paysage.feature.widget.preentation.widgetboard.adapter.holder

import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.preentation.widgetboard.model.ScrollableWidgetsGroupByPackageModel
import com.chekh.paysage.feature.widget.preentation.widgetboard.view.WidgetsDataView

class WidgetsDataViewHolder(
    private val view: WidgetsDataView,
    private val onScrollChange: (Int, String) -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.apply {
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            val padding = resources.getDimension(R.dimen.small).toInt()
            setPadding(padding, padding, padding, padding)
        }
    }

    fun bind(widgetsGroupByPackage: ScrollableWidgetsGroupByPackageModel) {
        setWidgets(
            widgetsGroupByPackage.data.widgets,
            isAnimate = false,
            onUpdated = { view.scrollOffset = widgetsGroupByPackage.scrollOffset }
        )
        view.setOffsetChangeListener { offset ->
            val id = widgetsGroupByPackage.data.widgetApp?.id ?: return@setOffsetChangeListener
            onScrollChange(offset, id)
        }
    }

    fun setWidgets(
        widgets: List<WidgetModel>,
        isAnimate: Boolean = true,
        onUpdated: (() -> Unit)? = null
    ) {
        view.setWidgets(widgets, isAnimate, onUpdated)
    }

    fun setScrollOffset(scrollOffset: Int) {
        view.scrollOffset = scrollOffset
    }
}
