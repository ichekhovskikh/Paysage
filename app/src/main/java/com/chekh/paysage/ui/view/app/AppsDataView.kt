package com.chekh.paysage.ui.view.app

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import com.chekh.paysage.R
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.ui.util.convertDpToPx

class AppsDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    init {
        layoutParams = MarginLayoutParams(MATCH_PARENT, convertDpToPx(80f).toInt())
        setBackgroundResource(R.drawable.background_grey_rounded)
    }

    fun bind(apps: List<AppInfo>) {

    }
}
