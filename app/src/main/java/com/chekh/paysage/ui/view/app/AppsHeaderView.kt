package com.chekh.paysage.ui.view.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import com.chekh.paysage.R
import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.ui.view.core.ArrowItemView

class AppsHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    val arrowItemView: ArrowItemView
    var categoryId: String? = null
        private set

    init {
        LayoutInflater.from(context).inflate(R.layout.view_apps_category_header, this, true)
        layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        background = context.getDrawable(R.color.white_alpha_95)
        arrowItemView = findViewById(R.id.title)
    }

    fun setCategory(category: CategoryInfo) {
        categoryId = category.id
        arrowItemView.setIcon(category.title.iconRes)
        arrowItemView.setTitleText(category.title.titleRes)
    }
}
