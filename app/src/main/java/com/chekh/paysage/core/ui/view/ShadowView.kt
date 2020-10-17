package com.chekh.paysage.core.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.isVisible
import com.chekh.paysage.R

class ShadowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : AppCompatImageView(context, attrs, defStyle) {

    init {
        visibility = View.GONE
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ShadowView, defStyle, 0)
        val drawable = attributes.getDrawable(R.styleable.ShadowView_shadowDrawable)
        if (drawable != null) {
            setImageDrawable(drawable)
        } else {
            setImageResource(R.drawable.background_navigation_shadow)
        }
        attributes.recycle()
    }

    var shadowHeight
        get() = layoutParams.height
        set(height) {
            isVisible = height > 0
            layoutParams.height = height
        }
}
