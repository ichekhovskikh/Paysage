package com.chekh.paysage.feature.main.presentation.desktop.tools

import android.graphics.Rect
import android.graphics.RectF

interface PageLocationProvider {
    fun getOccupiedCells(bounds: RectF): Rect
    fun getPageBounds(): Rect
}