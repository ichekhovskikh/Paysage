package com.chekh.paysage.feature.main.presentation.desktop.adapter

import com.chekh.paysage.core.ui.view.diffable.ListItemAdapter
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.tools.alphaColor
import com.chekh.paysage.core.ui.view.diffable.ListItem
import com.chekh.paysage.core.ui.view.flow.items.PixelFlowListItem
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetStyleModel
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopWidgetHostManager
import kotlinx.android.synthetic.main.item_desktop_widget_card.*

data class DesktopWidgetFlowListItem(
    private val widgetHostManager: DesktopWidgetHostManager,
    private val desktopWidget: DesktopWidgetModel
) : PixelFlowListItem {

    override val layout: Int = R.layout.item_desktop_widget_card
    override val id = desktopWidget.id
    override val x = desktopWidget.bounds.left
    override val y = desktopWidget.bounds.top
    override val width = desktopWidget.bounds.width()
    override val height = desktopWidget.bounds.height()
    val isDragging = desktopWidget.isDragging

    override fun bind(
        holder: ListItemAdapter.ListViewHolder,
        payloads: List<Any>
    ) = with(holder) {
        cvContent.setOnLongClickListener { true }
        val widgetId = desktopWidget.id.toIntOrNull()
        when {
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

    override fun getChangePayload(another: ListItem): Any? = Unit

    private companion object {
        const val DRAGGING_ALPHA = 0.2f
    }
}
