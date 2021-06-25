package com.chekh.paysage.core.ui.view.drag.delegate

import android.animation.ObjectAnimator
import android.graphics.*
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.graphics.toRect
import com.chekh.paysage.core.extension.copy
import com.chekh.paysage.core.extension.reStart
import com.chekh.paysage.core.ui.tools.makeBitmapScreenshot
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import java.lang.ref.WeakReference

class DragAndDropLayerDelegate(dragLayer: View) {

    private val dragLayerRef = WeakReference(dragLayer)
    private val listeners = mutableSetOf<DragAndDropListener>()
    private var thumbnail: Bitmap? = null
    private var data: ClipData? = null
    private var viewRect: RectF? = null
    private var targetViewRect: RectF? = null
    private var touchLocation: PointF? = null
    private var isDragAndDropStarted: Boolean = false

    private val startScaleAnimator = ObjectAnimator.ofFloat(1f, DRAG_START_SCALE).apply {
        var initialHeight = 0f
        var initialWidth = 0f
        doOnStart {
            initialHeight = viewRect?.height() ?: 0f
            initialWidth = viewRect?.width() ?: 0f
        }
        duration = DRAG_START_DURATION
        addUpdateListener { valueAnimator ->
            if (initialHeight == 0f) return@addUpdateListener
            if (initialWidth == 0f) return@addUpdateListener
            val recentViewRect = viewRect ?: return@addUpdateListener

            val animatedValue = valueAnimator.animatedValue as Float
            val dy = initialHeight * (animatedValue - 1) / 2
            val dx = initialWidth * (animatedValue - 1) / 2

            val left = recentViewRect.left - dx
            val top = recentViewRect.top - dy
            val right = recentViewRect.right + dx
            val bottom = recentViewRect.bottom + dy
            viewRect = RectF(left, top, right, bottom)
            dragLayerRef.get()?.invalidate()
        }
    }

    private val toTargetMoveAnimator = ObjectAnimator.ofFloat(0f, 1f).apply {
        var initialViewRectNullable: RectF? = null
        doOnStart { initialViewRectNullable = viewRect?.let { RectF(it) } }
        addUpdateListener { valueAnimator ->
            val initialViewRect = initialViewRectNullable ?: return@addUpdateListener
            val targetViewRect = targetViewRect ?: return@addUpdateListener

            val animatedValue = valueAnimator.animatedValue as Float
            val left = initialViewRect.left +
                (targetViewRect.left - initialViewRect.left) * animatedValue
            val top = initialViewRect.top +
                (targetViewRect.top - initialViewRect.top) * animatedValue
            val right = initialViewRect.right +
                (targetViewRect.right - initialViewRect.right) * animatedValue
            val bottom = initialViewRect.bottom +
                (targetViewRect.bottom - initialViewRect.bottom) * animatedValue
            viewRect = RectF(left, top, right, bottom)
            dragLayerRef.get()?.invalidate()
        }
        doOnEnd {
            if (!isDragAndDropStarted) {
                clearDrag()
            }
        }
    }

    fun addDragAndDropListener(listener: DragAndDropListener) {
        listeners.add(listener)
    }

    fun startDragAndDrop(view: View, data: ClipData?) {
        isDragAndDropStarted = true
        val thumbnail = view.makeBitmapScreenshot()
        this.thumbnail = thumbnail
        this.data = data
        val xy = IntArray(2)
        view.getLocationInWindow(xy)
        val (x, y) = xy.map(Int::toFloat)
        viewRect = RectF(x, y, x + thumbnail.width, y + thumbnail.height)
        targetViewRect = null
        startScaleAnimator.reStart()
        listeners.forEach { listener ->
            viewRect?.let { listener.onDragStart(it.copy(), data) }
        }
    }

    fun stopDragAndDrop() {
        if (!isDragAndDropStarted) return
        isDragAndDropStarted = false
        startScaleAnimator.cancel()
        if (targetViewRect != null) {
            toTargetMoveAnimator.reStart()
        } else {
            clearDrag()
        }
    }

    private fun clearDrag() {
        listeners.forEach { viewRect?.let { rect -> it.onDragEnd(rect.copy(), data) } }
        isDragAndDropStarted = false
        thumbnail = null
        data = null
        viewRect = null
        targetViewRect = null
        touchLocation = null
        dragLayerRef.get()?.invalidate()
    }

    fun setTargetDragViewBounds(bounds: RectF?) {
        if (!isDragAndDropStarted) return
        targetViewRect = bounds?.copy()
    }

    fun onInterceptTouchEvent() = isDragAndDropStarted || toTargetMoveAnimator.isRunning

    fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isDragAndDropStarted) return false
        when (event?.action) {
            ACTION_DOWN -> {
                touchLocation = PointF(event.x, event.y)
                return true
            }
            ACTION_MOVE -> {
                val newTouchLocation = PointF(event.x, event.y)
                val oldTouchLocation = touchLocation ?: newTouchLocation
                val dx = newTouchLocation.x - oldTouchLocation.x
                val dy = newTouchLocation.y - oldTouchLocation.y
                this.touchLocation = newTouchLocation
                viewRect?.offset(dx, dy)
                val rect = viewRect ?: return false
                listeners.forEach { it.onDragMove(newTouchLocation, rect.copy(), data) }
                return true
            }
            ACTION_CANCEL, ACTION_UP -> {
                touchLocation = null
                stopDragAndDrop()
                return true
            }
        }
        return false
    }

    fun draw(canvas: Canvas?) {
        if (!isDragAndDropStarted &&
            !toTargetMoveAnimator.isRunning &&
            !startScaleAnimator.isRunning ||
            canvas == null
        ) {
            return
        }
        val thumbnail = thumbnail ?: return
        val viewRect = viewRect?.toRect() ?: return
        canvas.save()
        canvas.drawBitmap(thumbnail, null, viewRect, null)
        canvas.restore()
    }

    private companion object {
        const val DRAG_START_DURATION = 150L
        const val DRAG_START_SCALE = 1.05f
    }
}
