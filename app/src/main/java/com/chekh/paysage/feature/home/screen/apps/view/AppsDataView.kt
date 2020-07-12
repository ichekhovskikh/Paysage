package com.chekh.paysage.feature.home.screen.apps.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.feature.home.screen.apps.adapter.AppListAdapter
import androidx.recyclerview.widget.GridLayoutManager
import com.chekh.paysage.feature.home.domain.model.AppListModel
import com.chekh.paysage.feature.home.domain.model.AppModel
import com.chekh.paysage.ui.anim.TransformAnimation

class AppsDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val adapter: AppListAdapter

    private val linearLayoutManager: LinearLayoutManager
    private val gridLayoutManager: GridLayoutManager
    private val transformAnimation: TransformAnimation
    private var onOffsetChange: ((Int) -> Unit)? = null
    private var onScrollStateChange: ((Int) -> Unit)? = null

    var isExpanded = false
        set(value) {
            if (field == value) return
            field = value
            layoutManager = if (field) gridLayoutManager else linearLayoutManager
        }

    var scrollOffset
        get() = computeHorizontalScrollOffset()
        set(value) {
            linearLayoutManager.scrollToPositionWithOffset(0, -value)
        }

    var appSize: Int = WRAP_CONTENT
        set(value) {
            field = value
            adapter.appSize = field
        }

    var spanCount
        get() = gridLayoutManager.spanCount
        set(value) {
            gridLayoutManager.spanCount = value
        }

    init {
        setBackgroundResource(R.drawable.background_grey_rounded)
        overScrollMode = View.OVER_SCROLL_NEVER
        clipToPadding = false

        isNestedScrollingEnabled = false
        linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        gridLayoutManager = GridLayoutManager(context, SPAN_COUNT_DEFAULT)
        layoutManager = linearLayoutManager

        adapter = AppListAdapter()
        setAdapter(adapter)
        transformAnimation = TransformAnimation(this)

        setupOffsetListener()
    }

    private fun setupOffsetListener() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(view: RecyclerView, newState: Int) {
                onScrollStateChange?.invoke(newState)
                if (newState != SCROLL_STATE_DRAGGING) {
                    onOffsetChange?.invoke(scrollOffset)
                }
            }
        })
    }

    fun setApps(apps: List<AppModel>) {
        adapter.setApps(apps)
    }

    fun setOffsetChangeListener(onOffsetChange: (Int) -> Unit) {
        this.onOffsetChange = onOffsetChange
    }

    fun setScrollStateChangeListener(onScrollStateChange: (Int) -> Unit) {
        this.onScrollStateChange = onScrollStateChange
    }

    fun setOnAnimationCancelListener(onCancel: () -> Unit) {
        transformAnimation.onAnimationCancelListener = onCancel
    }

    fun animatedExpand(isExpanded: Boolean) {
        if (this.isExpanded != isExpanded) {
            val oldHeight = measuredHeight
            layoutParams.height = oldHeight

            this.isExpanded = isExpanded
            measure(0, WRAP_CONTENT)
            val newHeight = measuredHeight
            transformAnimation.transform(oldHeight, newHeight)
        }
    }

    companion object {
        private const val SPAN_COUNT_DEFAULT = 4
    }
}
