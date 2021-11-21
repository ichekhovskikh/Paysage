package com.chekh.paysage.feature.main.apps.presentation.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.anim.TransformAnimation
import com.chekh.paysage.feature.main.common.domain.model.AppModel
import com.chekh.paysage.feature.main.apps.presentation.adapter.AppAdapter

class AppGroupDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RecyclerView(context, attrs, defStyle) {

    private val adapter: AppAdapter

    private val linearLayoutManager: LinearLayoutManager
    private val gridLayoutManager: GridLayoutManager
    private val transformAnimation: TransformAnimation
    private var onOffsetChange: ((Int) -> Unit)? = null

    var isExpanded = false
        private set

    var scrollOffset
        get() = computeHorizontalScrollOffset()
        set(value) {
            linearLayoutManager.scrollToPositionWithOffset(0, -value)
        }

    var spanCount
        get() = gridLayoutManager.spanCount
        set(value) {
            gridLayoutManager.spanCount = value
        }

    init {
        setBackgroundResource(R.drawable.bg_grey_rounded)
        overScrollMode = View.OVER_SCROLL_NEVER
        clipToPadding = false

        isNestedScrollingEnabled = false
        linearLayoutManager = LinearLayoutManager(context, HORIZONTAL, false)
        gridLayoutManager = GridLayoutManager(context, COLUMN_COUNT_DEFAULT)
        layoutManager = linearLayoutManager

        adapter = AppAdapter()
        setAdapter(adapter)
        transformAnimation = TransformAnimation(this)

        setupOffsetListener()
    }

    fun setAppSize(size: Int, isRequireUpdateImmediately: Boolean = true) {
        adapter.appSize = size
        if (isRequireUpdateImmediately) {
            adapter.notifyDataSetChanged()
        }
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

    fun setOnAnimationCancelListener(onCancel: () -> Unit) {
        transformAnimation.onAnimationCancelListener = onCancel
    }

    private fun setupOffsetListener() {
        addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(view: RecyclerView, newState: Int) {
                    if (newState == SCROLL_STATE_IDLE) {
                        onOffsetChange?.invoke(scrollOffset)
                    }
                }
            }
        )
    }

    private companion object {
        const val COLUMN_COUNT_DEFAULT = 4
        const val ANIMATION_DURATION_DEFAULT = 250L
    }
}
