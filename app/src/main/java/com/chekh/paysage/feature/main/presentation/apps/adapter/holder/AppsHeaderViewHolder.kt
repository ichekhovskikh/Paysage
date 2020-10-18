package com.chekh.paysage.feature.main.presentation.apps.adapter.holder

import android.util.TypedValue.COMPLEX_UNIT_PX
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.main.presentation.apps.model.ExpandableAppsGroupByCategoryModel
import com.chekh.paysage.feature.main.presentation.apps.view.AppsHeaderView

class AppsHeaderViewHolder(
    private val view: AppsHeaderView,
    private val onCategoryClick: (Int, String) -> Unit
) : RecyclerView.ViewHolder(view) {

    init {
        view.apply {
            layoutParams = ViewGroup.MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            val padding = resources.getDimension(R.dimen.small).toInt()
            val textSize = resources.getDimension(R.dimen.medium_text_size)
            setPadding(padding, padding, padding, padding)
            setTextSize(textSize, COMPLEX_UNIT_PX)
        }
    }

    fun bind(position: Int, appCategory: ExpandableAppsGroupByCategoryModel) = with(view) {
        val category = appCategory.data.category
        setOnClickListener { onCategoryClick(position, category.id) }
        setIcon(category.category.iconRes)
        setTitle(category.category.titleRes)
        view.expand(appCategory.isExpanded)
    }

    fun expand(isExpanded: Boolean) {
        view.expand(isExpanded, isAnimate = true)
    }
}
