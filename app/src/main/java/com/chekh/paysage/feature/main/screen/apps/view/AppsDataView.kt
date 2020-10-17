package com.chekh.paysage.feature.main.screen.apps.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.anim.TransformAnimation
import com.chekh.paysage.feature.main.domain.model.AppModel
import com.chekh.paysage.feature.main.screen.apps.adapter.AppListAdapter

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
        private set

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

    fun setApps(
        apps: List<AppModel>,
        isAnimate: Boolean = true,
        onUpdated: (() -> Unit)? = null
    ) {
        adapter.setApps(apps, isAnimate, onUpdated)
    }

    fun expand(
        isExpanded: Boolean,
        isAnimate: Boolean = false,
        duration: Long = ANIMATION_DURATION_DEFAULT
    ) {
        if (this.isExpanded != isExpanded) {
            this.isExpanded = isExpanded
            if (isAnimate) {
                val oldHeight = measuredHeight
                layoutParams.height = oldHeight
                layoutManager = if (isExpanded) gridLayoutManager else linearLayoutManager
                measure(0, WRAP_CONTENT)
                val newHeight = measuredHeight
                transformAnimation.transform(oldHeight, newHeight, duration)
            } else {
                layoutManager = if (isExpanded) gridLayoutManager else linearLayoutManager
            }
        }
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

    private fun setupOffsetListener() {
        addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(view: RecyclerView, newState: Int) {
                    onScrollStateChange?.invoke(newState)
                    if (newState == SCROLL_STATE_IDLE) {
                        onOffsetChange?.invoke(scrollOffset)
                    }
                }
            }
        )
    }

    private companion object {
        const val SPAN_COUNT_DEFAULT = 4
        const val ANIMATION_DURATION_DEFAULT = 250L
    }
}
