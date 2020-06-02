package com.chekh.paysage.feature.home.apps.view

import android.content.Context
import android.util.AttributeSet
import com.chekh.paysage.R
import com.chekh.paysage.ui.view.ArrowItemView

class AppsHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ArrowItemView(context, attrs, defStyle) {

    init {
        background = context.getDrawable(R.color.white_alpha_95)
    }
}
