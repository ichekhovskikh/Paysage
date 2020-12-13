package com.chekh.paysage.core.ui.view.drag.delegate

import android.animation.ObjectAnimator
import android.graphics.*
import android.view.MotionEvent
import android.view.MotionEvent.*
import android.view.View
import androidx.core.animation.doOnStart
import com.chekh.paysage.core.extension.copy
import com.chekh.paysage.core.extension.reReverse
import com.chekh.paysage.core.extension.reStart
import com.chekh.paysage.core.ui.tools.Size
import com.chekh.paysage.core.ui.tools.createColorAlpha
import com.chekh.paysage.core.ui.tools.paint
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragResizeListener
import java.lang.ref.WeakReference
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class DragResizeLayerDelegate(dragLayer: View) {

    private val dragLayerRef = WeakReference(dragLayer)
    private val listeners = mutableSetOf<DragResizeListener>()
    private var minSize: Size = Size.ZERO
    private var maxSize: Size = Size.ZERO
    private var data: ClipData? = null
    private var viewRect: RectF? = null
    private var targetViewRect: RectF? = null
    private var isDragResizeStarted: Boolean = false
    private var draggedSide: Side? = null
    private var distanceToRect: Float = 0f

    private val rectPaint = paint(Paint.Style.STROKE, PAINT_COLOR, PAINT_WIDTH)
    private val pointsPaint = paint(Paint.Style.FILL, PAINT_COLOR, PAINT_WIDTH)
    private val pointsAlphaPaint = paint(Paint.Style.FILL, PAINT_COLOR, PAINT_WIDTH)

    private enum class Side {
        LEFT, TOP, RIGHT, BOTTOM
    }

    private val toTargetChangeAnimator = ObjectAnimator.ofFloat(0f, 1f).apply {
        lateinit var initialViewRect: RectF
        doOnStart { initialViewRect = RectF(viewRect) }
        addUpdateListener { valueAnimator ->
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
    }

    private val onlyPointsAlphaAnimator = ObjectAnimator.ofFloat(1f, 0f).apply {
        addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            pointsAlphaPaint.color = createColorAlpha(PAINT_COLOR, animatedValue)
            dragLayerRef.get()?.invalidate()
        }
    }

    private val rectWithPointsAlphaAnimator = ObjectAnimator.ofFloat(1f, 0f).apply {
        addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            rectPaint.color = createColorAlpha(PAINT_COLOR, animatedValue)
            pointsPaint.color = createColorAlpha(PAINT_COLOR, animatedValue)
            pointsAlphaPaint.color = createColorAlpha(PAINT_COLOR, animatedValue)
            dragLayerRef.get()?.invalidate()
        }
    }

    private val isAnimating: Boolean
        get() = toTargetChangeAnimator.isRunning ||
            rectWithPointsAlphaAnimator.isRunning ||
            onlyPointsAlphaAnimator.isRunning

    fun addDragResizeListener(listener: DragResizeListener) {
        listeners.add(listener)
    }

    fun startDragResize(view: View, minSize: Size, maxSize: Size, data: ClipData?) {
        isDragResizeStarted = true
        rectPaint.color = PAINT_COLOR
        draggedSide = null
        distanceToRect = 0f
        this.minSize = minSize
        this.maxSize = maxSize
        this.data = data
        val xy = IntArray(2)
        view.getLocationInWindow(xy)
        val (x, y) = xy.map(Int::toFloat)
        viewRect = RectF(x, y, x + view.width, y + view.height)
        targetViewRect = null
        listeners.forEach { listener ->
            viewRect?.let { listener.onResizeStart(it.copy(), data) }
        }
        rectWithPointsAlphaAnimator.reReverse()
    }

    fun stopDragResize() {
        if (!isDragResizeStarted) return
        isDragResizeStarted = false
        toTargetChangeAnimator.cancel()
        rectWithPointsAlphaAnimator.cancel()
        onlyPointsAlphaAnimator.cancel()
        listeners.forEach { viewRect?.let { rect -> it.onResizeEnd(rect.copy(), data) } }
        isDragResizeStarted = false
        draggedSide = null
        distanceToRect = 0f
        minSize = Size.ZERO
        maxSize = Size.ZERO
        data = null
        viewRect = null
        targetViewRect = null
        dragLayerRef.get()?.invalidate()
    }

    fun setTargetDragViewBounds(bounds: RectF) {
        if (!isDragResizeStarted) return
        targetViewRect = bounds.copy()
    }

    fun onInterceptTouchEvent() = isDragResizeStarted

    fun onTouchEvent(event: MotionEvent?): Boolean {
        if (!isDragResizeStarted) return false
        if (event?.action == ACTION_DOWN) {
            calculateDragSide(event.x, event.y)
        }
        val draggedSide = draggedSide ?: return false
        when (event?.action) {
            ACTION_DOWN -> {
                onlyPointsAlphaAnimator.reStart()
                return true
            }
            ACTION_MOVE -> {
                val rect = viewRect?.let { RectF(it) } ?: return false
                rect.offsetTo(draggedSide, event.x, event.y, distanceToRect, minSize, maxSize)
                if (viewRect != rect) {
                    viewRect = rect
                    listeners.forEach { it.onResizeChange(rect.copy(), data) }
                    return true
                }
                return false
            }
            ACTION_CANCEL, ACTION_UP -> {
                onlyPointsAlphaAnimator.reReverse()
                if (targetViewRect != null) {
                    toTargetChangeAnimator.reStart()
                }
                return true
            }
        }
        return false
    }

    private fun calculateDragSide(x: Float, y: Float) {
        val viewRect = viewRect ?: return
        val sides = mutableListOf<Pair<Side, Float>>()
        if (viewRect.containsVertical(y, SENSITIVITY)) {
            sides.add(Side.LEFT to viewRect.left - x)
            sides.add(Side.RIGHT to viewRect.right - x)
        }
        if (viewRect.containsHorizontal(x, SENSITIVITY)) {
            sides.add(Side.TOP to viewRect.top - y)
            sides.add(Side.BOTTOM to viewRect.bottom - y)
        }
        val (closedSide, dValue) = sides.minByOrNull { (_, dValue) -> abs(dValue) } ?: return
        if (abs(dValue) < SENSITIVITY) {
            distanceToRect = dValue
            draggedSide = closedSide
        } else {
            distanceToRect = 0f
            draggedSide = null
        }
    }

    fun draw(canvas: Canvas?) {
        if (!isDragResizeStarted && !isAnimating || canvas == null) {
            return
        }
        val viewRect = viewRect ?: return
        canvas.save()
        canvas.drawRect(viewRect, rectPaint)
        canvas.drawCenterDragPoints(viewRect)
        canvas.restore()
    }

    private fun Canvas.drawCenterDragPoints(rect: RectF) {
        val cx = rect.left + rect.width() / 2
        val cy = rect.top + rect.height() / 2
        val radius = SENSITIVITY / 2
        if (draggedSide == Side.LEFT) {
            drawCircle(rect.left, cy, radius, pointsPaint)
        } else if (isDragResizeStarted) {
            drawCircle(rect.left, cy, radius, pointsAlphaPaint)
        }
        if (draggedSide == Side.RIGHT) {
            drawCircle(rect.right, cy, radius, pointsPaint)
        } else if (isDragResizeStarted) {
            drawCircle(rect.right, cy, radius, pointsAlphaPaint)
        }
        if (draggedSide == Side.TOP) {
            drawCircle(cx, rect.top, radius, pointsPaint)
        } else if (isDragResizeStarted) {
            drawCircle(cx, rect.top, radius, pointsAlphaPaint)
        }
        if (draggedSide == Side.BOTTOM) {
            drawCircle(cx, rect.bottom, radius, pointsPaint)
        } else if (isDragResizeStarted) {
            drawCircle(cx, rect.bottom, radius, pointsAlphaPaint)
        }
    }

    private fun RectF.containsHorizontal(x: Float, sensitivity: Float) =
        x > min(left, right) - sensitivity && x < max(left, right) + sensitivity

    private fun RectF.containsVertical(y: Float, sensitivity: Float) =
        y > min(top, bottom) - sensitivity && y < max(top, bottom) + sensitivity

    private fun RectF.offsetTo(
        side: Side,
        x: Float,
        y: Float,
        distanceToRect: Float,
        minSize: Size,
        maxSize: Size
    ) {
        when (side) {
            Side.LEFT -> {
                val width = right - x - distanceToRect
                left = when {
                    width < minSize.width -> right - minSize.width
                    width > maxSize.width -> right - maxSize.width
                    else -> x + distanceToRect
                }
            }
            Side.TOP -> {
                val height = bottom - y - distanceToRect
                top = when {
                    height < minSize.height -> bottom - minSize.height
                    height > maxSize.height -> bottom - maxSize.height
                    else -> y + distanceToRect
                }
            }
            Side.RIGHT -> {
                val width = x + distanceToRect - left
                right = when {
                    width < minSize.width -> left + minSize.width
                    width > maxSize.width -> left + maxSize.width
                    else -> x + distanceToRect
                }
            }
            Side.BOTTOM -> {
                val height = y + distanceToRect - top
                bottom = when {
                    height < minSize.height -> top + minSize.height
                    height > maxSize.height -> top + maxSize.height
                    else -> y + distanceToRect
                }
            }
        }
    }

    private companion object {
        const val SENSITIVITY = 40f
        const val PAINT_COLOR = Color.WHITE
        const val PAINT_WIDTH = 5f
    }
}
