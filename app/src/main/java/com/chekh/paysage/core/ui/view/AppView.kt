package com.chekh.paysage.core.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.chekh.paysage.R
import kotlinx.android.synthetic.main.view_app.view.*

class AppView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attrs, defStyle) {

    var label: CharSequence
        get() = tvLabel.text
        set(value) {
            tvLabel.text = value
        }

    var iconSize: Int
        get() = (layoutParams as MarginLayoutParams).height
        set(value) {
            ivIcon.layoutParams = (layoutParams as MarginLayoutParams).apply {
                height = value
                width = value
            }
        }

    var isLabelVisible: Boolean = false
        set(value) {
            field = value
            tvLabel.visibility = if (value) VISIBLE else GONE
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.view_app, this)
        orientation = VERTICAL
    }

    fun setIcon(icon: Bitmap?) {
        ivIcon.setImageBitmap(icon)
    }
}
