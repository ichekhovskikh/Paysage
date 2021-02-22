package com.chekh.paysage.feature.main.presentation.desktop.adapter

import android.widget.Toast
import com.chekh.paysage.core.ui.view.recycler.diffable.ListItemAdapter
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.tools.alphaColor
import com.chekh.paysage.core.ui.view.flow.items.IndexFlowListItem
import com.chekh.paysage.core.ui.view.recycler.diffable.ListItem
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetStyleModel
import com.chekh.paysage.feature.main.presentation.desktop.adapter.payload.DesktopWidgetBoundsChanged
import com.chekh.paysage.feature.main.presentation.desktop.adapter.payload.DesktopWidgetStyleChanged
import com.chekh.paysage.feature.main.presentation.desktop.adapter.payload.isDesktopWidgetBoundsChanged
import com.chekh.paysage.feature.main.presentation.desktop.adapter.payload.isDesktopWidgetStyleChanged
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import kotlinx.android.synthetic.main.item_desktop_widget_card.*

data class DesktopWidgetFlowListItem(
    private val widgetHostManager: DesktopWidgetHostManager,
    private val desktopWidget: DesktopWidgetModel
) : IndexFlowListItem {

    override val layout: Int = R.layout.item_desktop_widget_card
    override val id = desktopWidget.id
    override val columnIndex = desktopWidget.bounds.left
    override val rowIndex = desktopWidget.bounds.top
    override val columnCount = desktopWidget.bounds.width()
    override val rowCount = desktopWidget.bounds.height()
    val isDragging = desktopWidget.isDragging

    override fun bind(
        holder: ListItemAdapter.ListViewHolder,
        payloads: List<Any>?
    ) = with(holder) {
        val payload = payloads?.firstOrNull()
        cvContent.setOnLongClickListener { true }
        val widgetId = desktopWidget.id.toIntOrNull()
        when {
            payload is DesktopWidgetBoundsChanged -> return@with
            payload is DesktopWidgetStyleChanged -> setStyle(payload.style)
            widgetId == null || desktopWidget.isDragging -> bindStub()
            else -> bindWidget(widgetId)
        }
    }

    private fun ListItemAdapter.ListViewHolder.bindWidget(widgetId: Int) {
        cvContent.removeAllViews()
        val widgetView = widgetHostManager.attach(
            widgetId,
            desktopWidget.packageName,
            desktopWidget.className
        )
        cvContent.addView(widgetView)
        widgetView?.setOnClickListener {
            Toast.makeText(it.context, "test", Toast.LENGTH_SHORT).show()
        }
        setStyle(desktopWidget.style)
    }

    private fun ListItemAdapter.ListViewHolder.bindStub() {
        cvContent.removeAllViews()
        setStyle(desktopWidget.style)
    }

    private fun ListItemAdapter.ListViewHolder.setStyle(style: DesktopWidgetStyleModel) {
        val contentAlpha = if (desktopWidget.isDragging) DRAGGING_ALPHA else style.alpha
        cvContent.setCardBackgroundColor(alphaColor(style.color, contentAlpha))
        cvContent.cardElevation = style.elevation.toFloat()
        cvContent.radius = style.corner.toFloat()
    }

    override fun hasSameContentsAs(another: ListItem) =
        another is DesktopWidgetFlowListItem && this.desktopWidget == another.desktopWidget

    override fun getChangePayload(another: ListItem): Any? = when {
        another !is DesktopWidgetFlowListItem -> null
        isDesktopWidgetStyleChanged(desktopWidget, another.desktopWidget) -> {
            DesktopWidgetStyleChanged(another.desktopWidget.style)
        }
        isDesktopWidgetBoundsChanged(desktopWidget, another.desktopWidget) -> {
            DesktopWidgetBoundsChanged(another.desktopWidget.bounds)
        }
        else -> null
    }

    private companion object {
        const val DRAGGING_ALPHA = 0.2f
    }
}
