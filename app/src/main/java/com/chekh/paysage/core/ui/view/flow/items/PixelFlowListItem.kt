package com.chekh.paysage.core.ui.view.flow.items

import kotlin.math.max
import kotlin.math.round

interface PixelFlowListItem : FlowListItem {
    val x: Int
    val y: Int
    val width: Int
    val height: Int

    override fun getX(columnWidth: Int) =
        round(x.toFloat() / columnWidth).toInt() * columnWidth

    override fun getY(rowHeight: Int) =
        round(y.toFloat() / rowHeight).toInt() * rowHeight

    override fun getWidth(columnWidth: Int) =
        max(round(width.toFloat() / columnWidth).toInt(), MIN_SPAN_COUNT) * columnWidth

    override fun getHeight(rowHeight: Int) =
        max(round(height.toFloat() / rowHeight).toInt(), MIN_SPAN_COUNT) * rowHeight

    private companion object {
        const val MIN_SPAN_COUNT = 1
    }
}