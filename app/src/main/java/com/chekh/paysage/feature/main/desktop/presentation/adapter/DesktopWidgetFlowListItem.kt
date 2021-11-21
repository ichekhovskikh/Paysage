package com.chekh.paysage.feature.main.desktop.presentation.adapter

import androidx.cardview.widget.CardView
import com.chekh.paysage.core.ui.view.recycler.diffable.ListItemAdapter
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.tools.alphaColor
import com.chekh.paysage.core.ui.view.flow.items.IndexFlowListItem
import com.chekh.paysage.core.ui.view.recycler.diffable.ListItem
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetModel
import com.chekh.paysage.feature.main.common.domain.model.DesktopWidgetStyleModel
import com.chekh.paysage.feature.main.desktop.presentation.adapter.payload.DesktopWidgetBoundsChanged
import com.chekh.paysage.feature.main.desktop.presentation.adapter.payload.DesktopWidgetStyleChanged
import com.chekh.paysage.feature.main.desktop.presentation.adapter.payload.isDesktopWidgetBoundsChanged
import com.chekh.paysage.feature.main.desktop.presentation.adapter.payload.isDesktopWidgetStyleChanged
import com.chekh.paysage.feature.main.desktop.presentation.tools.DesktopWidgetHostManager
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
            payload is DesktopWidgetStyleChanged -> cvContent.setStyle(payload.style)
            widgetId == null || desktopWidget.isDragging -> bindStub()
            else -> bindWidget(widgetId)
        }
    }

    private fun ListItemAdapter.ListViewHolder.bindWidget(widgetId: Int) {
        cvContent.removeAllViews()
        widgetHostManager.attach(
            widgetId,
            desktopWidget.packageName,
            desktopWidget.className
        )?.let { widgetView ->
            widgetView.isLongClickable = true
            cvContent.addView(widgetView)
        }
        cvContent.setStyle(desktopWidget.style)
    }

    private fun ListItemAdapter.ListViewHolder.bindStub() {
        cvContent.removeAllViews()
        cvContent.setStyle(desktopWidget.style)
    }

    private fun CardView.setStyle(style: DesktopWidgetStyleModel) {
        val contentAlpha = if (desktopWidget.isDragging) DRAGGING_ALPHA else style.alpha
        setCardBackgroundColor(alphaColor(style.color, contentAlpha))
        cardElevation = style.elevation.toFloat()
        radius = style.corner.toFloat()
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
