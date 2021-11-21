package com.chekh.paysage.feature.main.desktop.presentation.tools

import android.graphics.Rect
import android.graphics.RectF

interface PageLocationProvider {
    fun getOccupiedCells(bounds: RectF): Rect
    fun getPageBounds(): Rect
}
