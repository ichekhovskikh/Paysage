package com.chekh.paysage.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.chekh.paysage.R

class RoundedFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private var topLeftCornerRadius: Float = 0f
    private var topRightCornerRadius: Float = 0f
    private var bottomLeftCornerRadius: Float = 0f
    private var bottomRightCornerRadius: Float = 0f

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RoundedFrameLayout)
        attributes.apply {
            val defaultCornerRadius = getDimension(R.styleable.RoundedFrameLayout_cornerRadius, 0f)
            val topLeft = getDimension(
                R.styleable.RoundedFrameLayout_topLeftCornerRadius,
                defaultCornerRadius
            )
            val topRight = getDimension(
                R.styleable.RoundedFrameLayout_topRightCornerRadius,
                defaultCornerRadius
            )
            val bottomLeft = getDimension(
                R.styleable.RoundedFrameLayout_bottomLeftCornerRadius,
                defaultCornerRadius
            )
            val bottomRight = getDimension(
                R.styleable.RoundedFrameLayout_bottomRightCornerRadius,
                defaultCornerRadius
            )
            setCornerRadius(topLeft, topRight, bottomLeft, bottomRight)
        }
        attributes.recycle()
        setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    }

    private fun setCornerRadius(
        topLeft: Float = topLeftCornerRadius,
        topRight: Float = topRightCornerRadius,
        bottomLeft: Float = bottomLeftCornerRadius,
        bottomRight: Float = bottomRightCornerRadius
    ) {
        topLeftCornerRadius = topLeft
        topRightCornerRadius = topRight
        bottomLeftCornerRadius = bottomLeft
        bottomRightCornerRadius = bottomRight
        invalidate()
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
}