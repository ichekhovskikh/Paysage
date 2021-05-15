package com.chekh.paysage.core.ui.view.drag

import android.graphics.PointF
import android.graphics.RectF

interface DragAndDropListener {
    fun onDragStart(location: RectF, data: ClipData?) {}
    fun onDragMove(touch: PointF?, location: RectF, data: ClipData?) {}
    fun onDragEnd(location: RectF, data: ClipData?) {}
}
