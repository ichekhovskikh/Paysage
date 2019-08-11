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

    init {
        LayoutInflater.from(context).inflate(R.layout.view_apps_category_header, this, true)
        layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        background = context.getDrawable(R.color.white_alpha_80)
    }

    var cachedArrowItemView: ArrowItemView? = null

    val arrowItemView: ArrowItemView?
        get() {
            if (cachedArrowItemView != null) {
                return cachedArrowItemView
            }
            for (index in 0 until childCount) {
                val child = getChildAt(index)
                if (child is ArrowItemView) {
                    return child
                }
            }
            return null
        }

    fun bind(category: CategoryInfo) {
        arrowItemView?.let {
            it.setIcon(category.title.iconRes)
            it.setTitleText(category.title.titleRes)
        }
    }

    override fun delegateEvents(view: View) {
        if (view is AppsHeaderView) {
            arrowItemView?.setOnExpandedClickListener {
                view.arrowItemView?.isExpanded = it
            }
        }
    }

    override fun copyState(view: View) {
        if (view is AppsHeaderView && view.arrowItemView != null) {
            val copyArrowItemView = view.arrowItemView as ArrowItemView
            arrowItemView?.let {
                it.nonAnimationExpand(copyArrowItemView.isExpanded)
                it.setIcon(copyArrowItemView.icon)
                it.setTitleText(copyArrowItemView.title)
            }
        }
    }
}
