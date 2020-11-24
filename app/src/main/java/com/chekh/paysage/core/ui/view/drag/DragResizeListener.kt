package com.chekh.paysage.core.ui.view.drag

import android.graphics.RectF

interface DragResizeListener {
    fun onResizeStart(location: RectF, data: ClipData?) {}
    fun onResizeChange(location: RectF, data: ClipData?) {}
    fun onResizeEnd(location: RectF, data: ClipData?) {}
}
