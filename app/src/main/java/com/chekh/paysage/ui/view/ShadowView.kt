package com.chekh.paysage.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import com.chekh.paysage.R

class ShadowView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ImageView(context, attrs, defStyle) {

    init {
        visibility = View.GONE
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ShadowView, defStyle, 0)
        val drawable = attributes.getDrawable(R.styleable.ShadowView_shadowDrawable)
        if (drawable != null) {
            setImageDrawable(drawable)
        } else {
            setImageResource(R.drawable.navigation_shadow)
        }
        attributes.recycle()
    }

    var shadowHeight
        get() = layoutParams.height
        set(height) {
            if (height > 0) {
                layoutParams.height = height
                visibility = View.VISIBLE
            } else visibility = View.GONE
        }
}