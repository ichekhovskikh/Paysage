package com.chekh.paysage.core.ui.view.flow.items

import com.chekh.paysage.core.ui.view.recycler.diffable.ListItem

interface FlowListItem : ListItem {
    fun getX(columnWidth: Int): Int
    fun getY(rowHeight: Int): Int
    fun getWidth(columnWidth: Int): Int
    fun getHeight(rowHeight: Int): Int
}
