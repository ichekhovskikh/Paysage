package com.chekh.paysage.core.ui.pager

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import me.everything.android.ui.overscroll.HorizontalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.IOverScrollDecoratorAdapter

fun ViewPager2.setBouncing(isBouncing: Boolean) {
    val decorAdapter = ViewPagerOverScrollDecorAdapter(this)
    val overScrollDecorator = when (orientation) {
        ViewPager2.ORIENTATION_VERTICAL -> VerticalOverScrollBounceEffectDecorator(decorAdapter)
        else -> HorizontalOverScrollBounceEffectDecorator(decorAdapter)
    }
    val touchListener = if (isBouncing) overScrollDecorator else null
    val child = getChildAt(0)
    child.setOnTouchListener(touchListener)
    if (child is RecyclerView) {
        child.setOverScrollMode(View.OVER_SCROLL_NEVER)
    }
}

class ViewPagerOverScrollDecorAdapter(
    private val viewPager: ViewPager2
) : OnPageChangeCallback(), IOverScrollDecoratorAdapter {

    private var lastPagerPosition = viewPager.currentItem
    private var lastPagerScrollOffset = 0f

    init {
        viewPager.registerOnPageChangeCallback(this)
    }

    override fun getView() = viewPager

    override fun isInAbsoluteStart() = lastPagerPosition == 0 && lastPagerScrollOffset == 0f

    override fun isInAbsoluteEnd(): Boolean {
        val adapter = viewPager.adapter ?: return true
        return lastPagerPosition == adapter.itemCount - 1 && lastPagerScrollOffset == 0f
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        lastPagerPosition = position
        lastPagerScrollOffset = positionOffset
    }
}
