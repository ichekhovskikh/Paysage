package com.chekh.paysage.feature.main.presentation.desktop.adapter

import android.annotation.SuppressLint
import com.chekh.paysage.core.ui.view.diffable.ListItemAdapter
import com.chekh.paysage.core.ui.view.flow.FlowListItem
import kotlinx.android.synthetic.main.card_view_test.*
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.view.diffable.ListItem

data class WidgetFlowListItem(
    override var id: String,
    override var columnIndex: Int,
    override var rowIndex: Int,
    override var columnCount: Int,
    override var rowCount: Int
) : FlowListItem {

    override val layout: Int = R.layout.card_view_test

    @SuppressLint("SetTextI18n")
    override fun bind(
        holder: ListItemAdapter.ListViewHolder,
        payloads: List<Any>
    ) = with(holder) {
        text_view.text = "${columnCount}x$rowCount"
    }

    override fun getChangePayload(another: ListItem): Any? = Unit
}
