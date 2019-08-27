package com.chekh.paysage.ui.view.app

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.LinearLayout.HORIZONTAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.model.AppInfo
import com.chekh.paysage.ui.adapter.AppsListAdapter
import com.chekh.paysage.ui.util.convertDpToPx
import androidx.recyclerview.widget.GridLayoutManager
import com.chekh.paysage.ui.anim.TransformAnimation

class AppsDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : FrameLayout(context, attrs, defStyle) {

    private lateinit var appsView: RecyclerView
    private lateinit var adapter: AppsListAdapter

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var gridLayoutManager: GridLayoutManager
    private var transformAnimation: TransformAnimation

    var isExpanded = false
        private set

    init {
        layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
        minimumHeight = resources.getDimension(R.dimen.default_minimum_categories_size).toInt()
        //TODO set padding
        setBackgroundResource(R.drawable.background_grey_rounded)
        initAppsView()
        transformAnimation = TransformAnimation(appsView)
        addView(appsView)
    }

    private fun initAppsView() {
        linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        gridLayoutManager = GridLayoutManager(context, 2)
        appsView = RecyclerView(context).apply {
            layoutParams = MarginLayoutParams(MATCH_PARENT, WRAP_CONTENT)
            overScrollMode = View.OVER_SCROLL_NEVER
            layoutManager = linearLayoutManager
            clipToPadding = false
            val padding = convertDpToPx(6f).toInt()
            setPadding(padding, padding, padding, padding)
        }
        adapter = AppsListAdapter()
        appsView.adapter = adapter
    }

    fun bind(apps: List<AppInfo>) {
        adapter.setApps(apps)
    }

    fun expand(expanded: Boolean) {
        if (isExpanded != expanded) {
            isExpanded = expanded
            appsView.layoutManager = if (isExpanded) gridLayoutManager else linearLayoutManager
        }
    }

    fun animationExpand(expanded: Boolean) {
        if (isExpanded != expanded) {
            isExpanded = expanded
            val currentHeight = appsView.measuredHeight
            appsView.layoutParams.height = currentHeight
            appsView.layoutManager = if (isExpanded) gridLayoutManager else linearLayoutManager
            appsView.measure(MATCH_PARENT, WRAP_CONTENT)
            val newHeight = appsView.measuredHeight
            transformAnimation.transform(currentHeight, newHeight)
        }
    }
}
