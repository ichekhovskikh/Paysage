package com.chekh.paysage.ui.view.app

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import com.chekh.paysage.R
import com.chekh.paysage.model.CategoryInfo
import com.chekh.paysage.ui.view.core.ArrowItemView
import com.chekh.paysage.ui.view.core.stickyheader.StickyHeaderView

class AppsHeaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : StickyHeaderView(context, attrs, defStyle) {

    val arrowItemView: ArrowItemView

    init {
        LayoutInflater.from(context).inflate(R.layout.view_apps_category_header, this, true)
        layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        background = context.getDrawable(R.color.white)
        arrowItemView = findViewById(R.id.title)
    }

    fun bind(category: CategoryInfo) {
        arrowItemView.setIcon(category.title.iconRes)
        arrowItemView.setTitleText(category.title.titleRes)
    }

    override fun delegateEvents(view: View) {
        if (view is AppsHeaderView) {
            arrowItemView.setOnExpandedClickListener {
                view.arrowItemView.nonAnimationExpand(it)
            }
        }
    }

    override fun copyState(view: View) {
        if (view is AppsHeaderView) {
            arrowItemView.apply {
                nonAnimationExpand(view.arrowItemView.isExpanded)
                setIcon(view.arrowItemView.icon)
                setTitleText(view.arrowItemView.title)
            }
        }
    }
}
