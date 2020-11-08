package com.chekh.paysage.core.ui.view.flow

import com.chekh.paysage.core.ui.view.diffable.ListItem

interface FlowListItem : ListItem {
    var columnIndex: Int
    var rowIndex: Int
    var columnCount: Int
    var rowCount: Int
}
