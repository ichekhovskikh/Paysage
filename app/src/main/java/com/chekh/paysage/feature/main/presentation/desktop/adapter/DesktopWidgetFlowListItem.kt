package com.chekh.paysage.feature.main.presentation.desktop.adapter

import android.annotation.SuppressLint
import com.chekh.paysage.core.ui.view.diffable.ListItemAdapter
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.view.diffable.ListItem
import com.chekh.paysage.core.ui.view.flow.items.PixelFlowListItem
import com.chekh.paysage.feature.main.domain.model.DesktopWidgetModel
import kotlinx.android.synthetic.main.item_desktop_widget_card.*

// TODO WIP
data class DesktopWidgetFlowListItem(
    private val desktopWidget: DesktopWidgetModel
) : PixelFlowListItem {

    override val id = desktopWidget.id

    override val x = desktopWidget.x
    override val y = desktopWidget.y
    override val width = desktopWidget.width
    override val height = desktopWidget.height

    override val layout: Int = R.layout.item_desktop_widget_card

    @SuppressLint("SetTextI18n")
    override fun bind(
        holder: ListItemAdapter.ListViewHolder,
        payloads: List<Any>
    ) = with(holder) {
        text_view.text = "${width}x$height"
        text_view.setOnLongClickListener {
            true
        }
    }

    override fun getChangePayload(another: ListItem): Any? = Unit
}
