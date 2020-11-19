package com.chekh.paysage.feature.widget.presentation.widgetboard.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.view.ArrowItemView

class WidgetGroupHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ArrowItemView(context, attrs, defStyle) {

    init {
        background = ContextCompat.getDrawable(context, R.color.whiteAlpha95)
        isArrowVisible = false
        isClickable = false
        isFocusable = false
    }
}
