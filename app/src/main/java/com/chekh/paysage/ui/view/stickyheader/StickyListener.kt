package com.chekh.paysage.ui.view.stickyheader

import android.view.View

interface StickyListener {
    fun getHeaderPositionForItem(itemPosition: Int): Int

    fun getHeaderLayout(headerPosition: Int): Int

    fun bindHeaderData(header: View, headerPosition: Int)

    fun isHeader(itemPosition: Int): Boolean
}
