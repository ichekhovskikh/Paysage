package com.chekh.paysage.core.ui.view.drag

import android.content.Context
import android.graphics.Canvas
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import com.chekh.paysage.core.ui.tools.Size
import com.chekh.paysage.core.ui.tools.on
import com.chekh.paysage.core.ui.view.drag.delegate.DragAndDropLayerDelegate
import com.chekh.paysage.core.ui.view.drag.delegate.DragResizeLayerDelegate

class DragLayerLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private val dragAndDropLayerDelegate = DragAndDropLayerDelegate(this)
    private val dragResizeLayerDelegate = DragResizeLayerDelegate(this)

    fun addDragAndDropListener(listener: DragAndDropListener) {
        dragAndDropLayerDelegate.addDragAndDropListener(listener)
    }

    fun addDragResizeListener(listener: DragResizeListener) {
        dragResizeLayerDelegate.addDragResizeListener(listener)
    }

    fun startDragAndDrop(
        view: View,
        data: ClipData? = null
    ) {
        dragResizeLayerDelegate.stopDragResize()
        dragAndDropLayerDelegate.startDragAndDrop(view, data)
    }

    fun startDragResize(
        view: View,
        minSize: Size? = null,
        maxSize: Size? = null,
        data: ClipData? = null
    ) {
        dragAndDropLayerDelegate.stopDragAndDrop()
        dragResizeLayerDelegate.startDragResize(
            view,
            minSize ?: Size.ZERO,
            maxSize ?: width on height,
            data
        )
    }

    fun stopDragAndDrop() {
        dragAndDropLayerDelegate.stopDragAndDrop()
    }

    fun stopDragResize() {
        dragResizeLayerDelegate.stopDragResize()
    }

    fun setTargetDragViewBounds(bounds: RectF) {
        dragAndDropLayerDelegate.setTargetDragViewBounds(bounds)
        dragResizeLayerDelegate.setTargetDragViewBounds(bounds)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        var needInvalidate = false
        var isIntercepted = false
        if (dragAndDropLayerDelegate.onInterceptTouchEvent()) {
            needInvalidate = needInvalidate or dragAndDropLayerDelegate.onTouchEvent(event)
            isIntercepted = true
        }
        if (dragResizeLayerDelegate.onInterceptTouchEvent()) {
            needInvalidate = needInvalidate or dragResizeLayerDelegate.onTouchEvent(event)
            isIntercepted = true
        }
        if (needInvalidate) {
            invalidate()
            return true
        }
        return isIntercepted || super.dispatchTouchEvent(event)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        dragAndDropLayerDelegate.draw(canvas)
        dragResizeLayerDelegate.draw(canvas)
    }
}
