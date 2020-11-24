package com.chekh.paysage.feature.widget.presentation.widgetboard.adapter.holder

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.widget.domain.model.WidgetModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.model.WidgetGroupModel
import com.chekh.paysage.feature.widget.presentation.widgetboard.view.WidgetGroupDataView

class WidgetGroupDataViewHolder(
    private val view: WidgetGroupDataView,
    private val onScrollChanged: (Int, String) -> Unit,
    private val onStartDragAndDrop: ((View, WidgetModel) -> Unit)? = null
) : RecyclerView.ViewHolder(view) {

    init {
        view.apply {
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            val padding = resources.getDimension(R.dimen.small).toInt()
            setPadding(padding, padding, padding, padding)
            setOnStartAndDropListener(onStartDragAndDrop)
        }
    }

    fun bind(widgetsGroup: WidgetGroupModel) {
        setWidgets(
            widgetsGroup.data.widgets,
            isAnimate = false,
            onUpdated = { view.scrollOffset = widgetsGroup.scrollOffset }
        )
        view.setOffsetChangeListener { offset ->
            val id = widgetsGroup.data.app?.id ?: return@setOffsetChangeListener
            onScrollChanged(offset, id)
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
