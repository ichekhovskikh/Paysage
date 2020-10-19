package com.chekh.paysage.core.ui.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.constraintlayout.widget.ConstraintLayout
import com.chekh.paysage.R

class OutlineConstraintLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.OutlineConstraintLayout)
        attributes.apply {
            val outlineRadius = getDimension(R.styleable.OutlineConstraintLayout_outlineRadius, 0f)
            if (outlineRadius > 0) {
                outlineProvider = RoundedOutlineProvider(outlineRadius)
                clipToOutline = true
            }
        }
        attributes.recycle()
    }

    private class RoundedOutlineProvider(private val radius: Float) : ViewOutlineProvider() {

        override fun getOutline(view: View, outline: Outline) {
            outline.setRoundRect(
                0,
                0,
                view.width,
                (view.height + radius).toInt(),
                radius
            )
        }
    }
}
