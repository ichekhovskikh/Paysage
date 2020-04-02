package com.chekh.paysage.feature.home.apps.view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.chekh.paysage.R
import com.chekh.paysage.extension.applyPadding
import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.ui.util.MetricsConverter
import com.chekh.paysage.ui.view.ArrowItemView

class AppsHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ArrowItemView(context, attrs, defStyle) {

    var categoryId: String? = null
        private set

    init {
        layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        background = context.getDrawable(R.color.white_alpha_95)
        val padding = MetricsConverter(context).dpToPx(8f)
        setTitleTextSize(16f)
        applyPadding(padding, padding, padding, padding)
    }

    fun setCategory(category: CategoryInfo) {
        categoryId = category.id
        setIcon(category.title.iconRes)
        setTitleText(category.title.titleRes)
    }
}
