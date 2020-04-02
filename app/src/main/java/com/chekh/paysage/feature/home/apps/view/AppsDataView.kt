package com.chekh.paysage.feature.home.apps.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.feature.home.apps.adapter.AppsListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.chekh.paysage.ui.anim.TransformAnimation
import com.chekh.paysage.ui.util.MetricsConverter

class AppsDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val adapter: AppsListAdapter

    private val linearLayoutManager: LinearLayoutManager
    private val gridLayoutManager: GridLayoutManager
    private val transformAnimation: TransformAnimation

    private var expanded = false

    var isExpanded
        get() = expanded
        set(value) {
            if (this.expanded == value) return
            this.expanded = value
            layoutManager = if (this.expanded) gridLayoutManager else linearLayoutManager
        }

    var appsScrollX = 0
        get() = computeHorizontalScrollOffset()
        set(value) {
            field = value
            linearLayoutManager.scrollToPositionWithOffset(0, -value)
        }

    init {
        layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        minimumHeight = resources.getDimension(R.dimen.default_minimum_categories_size).toInt()
        setBackgroundResource(R.drawable.background_grey_rounded)
        overScrollMode = View.OVER_SCROLL_NEVER
        clipToPadding = false
        val padding = MetricsConverter(context).dpToPx(6f)
        setPadding(padding, padding, padding, padding)

        linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        gridLayoutManager = GridLayoutManager(context, 2)
        layoutManager = linearLayoutManager

        adapter = AppsListAdapter()
        setAdapter(adapter)
        transformAnimation = TransformAnimation(this)
    }

    fun setApps(apps: List<AppInfo>) {
        adapter.setApps(apps)
    }

    fun setOnAnimationCancelListener(onCancel: () -> Unit) {
        transformAnimation.onAnimationCancelListener = onCancel
    }

    fun animatedExpand(expanded: Boolean) {
        if (this.expanded != expanded) {
            this.expanded = expanded
            val currentHeight = measuredHeight
            layoutParams.height = currentHeight
            layoutManager = if (this.expanded) gridLayoutManager else linearLayoutManager
            measure(0, WRAP_CONTENT)
            val newHeight = measuredHeight
            transformAnimation.transform(currentHeight, newHeight)
        }
    }
}
