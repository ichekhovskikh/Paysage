package com.chekh.paysage.core.ui.view.flow.items

interface IndexFlowListItem : FlowListItem {
    val columnIndex: Int
    val rowIndex: Int
    val columnCount: Int
    val rowCount: Int

    override fun getX(columnWidth: Int) = columnIndex * columnWidth
    override fun getY(rowHeight: Int) = rowIndex * rowHeight
    override fun getWidth(columnWidth: Int) = columnCount * columnWidth
    override fun getHeight(rowHeight: Int) = rowCount * rowHeight
}
