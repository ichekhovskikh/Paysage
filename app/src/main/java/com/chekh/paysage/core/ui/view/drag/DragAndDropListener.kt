package com.chekh.paysage.core.ui.view.drag

import android.graphics.RectF

interface DragAndDropListener {
    fun onDragStart(location: RectF, data: ClipData?) {}
    fun onDragMove(location: RectF, data: ClipData?) {}
    fun onDragEnd(location: RectF, data: ClipData?) {}
}
