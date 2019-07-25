package com.chekh.paysage.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chekh.paysage.R

class RoundedView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private var topLeftCornerRadius: Float = 0f
    private var topRightCornerRadius: Float = 0f
    private var bottomLeftCornerRadius: Float = 0f
    private var bottomRightCornerRadius: Float = 0f

    init {
        val attributes = context.obtainStyledAttributes( attrs, R.styleable.RoundedView)
        attributes.apply {
            topLeftCornerRadius = getDimension(R.styleable.RoundedView_topLeftCornerRadius, 0f)
            topRightCornerRadius = getDimension(R.styleable.RoundedView_topRightCornerRadius, 0f)
            bottomLeftCornerRadius = getDimension(R.styleable.RoundedView_bottomLeftCornerRadius, 0f)
            bottomRightCornerRadius = getDimension(R.styleable.RoundedView_bottomRightCornerRadius, 0f)
        }
        attributes.recycle()
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    override fun dispatchDraw(canvas: Canvas) {
        val path = Path()
        val cornerDimensions = floatArrayOf(
            topLeftCornerRadius,
            topLeftCornerRadius,
            topRightCornerRadius,
            topRightCornerRadius,
            bottomRightCornerRadius,
            bottomRightCornerRadius,
            bottomLeftCornerRadius,
            bottomLeftCornerRadius
        )
        val rectangle = RectF(0f, 0f, canvas.width.toFloat(), canvas.height.toFloat())
        path.addRoundRect(rectangle, cornerDimensions, Path.Direction.CW)
        canvas.clipPath(path)
        super.dispatchDraw(canvas)
    }

    fun setTopLeftCornerRadius(topLeftCornerRadius: Float) {
        this.topLeftCornerRadius = topLeftCornerRadius
        invalidate()
    }

    fun setTopRightCornerRadius(topRightCornerRadius: Float) {
        this.topRightCornerRadius = topRightCornerRadius
        invalidate()
    }

    fun setBottomLeftCornerRadius(bottomLeftCornerRadius: Float) {
        this.bottomLeftCornerRadius = bottomLeftCornerRadius
        invalidate()
    }

    fun setBottomRightCornerRadius(bottomRightCornerRadius: Float) {
        this.bottomRightCornerRadius = bottomRightCornerRadius
        invalidate()
    }
}