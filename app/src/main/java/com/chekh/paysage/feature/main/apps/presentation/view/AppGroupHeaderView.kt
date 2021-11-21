package com.chekh.paysage.feature.main.apps.presentation.view

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.view.ArrowItemView

class AppGroupHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ArrowItemView(context, attrs, defStyle) {

    init {
        background = ContextCompat.getDrawable(context, R.color.whiteAlpha95)
    }
}
