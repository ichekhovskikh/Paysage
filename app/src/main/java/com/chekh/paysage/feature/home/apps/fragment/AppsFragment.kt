package com.chekh.paysage.feature.home.apps.fragment

import android.os.Bundle
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import com.chekh.paysage.R
import com.chekh.paysage.extension.applyPadding
import com.chekh.paysage.ui.fragment.ViewModelFragment
import com.chekh.paysage.feature.home.apps.adapter.AppsCategoryAdapter
import com.chekh.paysage.extension.observe
import com.chekh.paysage.ui.view.smoothscroll.SmoothScrollLinearLayoutManager
import com.chekh.paysage.feature.home.HomeViewModel
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout.PanelState
import kotlinx.android.synthetic.main.fragment_apps.*

class AppsFragment : ViewModelFragment<HomeViewModel>() {

    override val layoutId = R.layout.fragment_apps
    override val viewModelClass = HomeViewModel::class.java

    private var adapter: AppsCategoryAdapter? = null
    private var slidingPanel: SlidingUpPanelLayout? = null

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        super.onViewModelCreated(savedInstanceState)
        adapter = AppsCategoryAdapter()
        onAppsAdapterCreated()

        val defaultPaddingBottom = categoryRecycler.paddingBottom
        viewModel.windowInsets.observe(this) { insets ->
            val height = insets.systemWindowInsetBottom
            categoryRecycler.applyPadding(bottom = defaultPaddingBottom + height)
        }
        viewModel.getAppsGroupByCategories(this) { categories ->
            adapter?.setAppsCategories(categories)
        }
    }

    private fun onAppsAdapterCreated() {
        categoryRecycler.apply {
            layoutManager = SmoothScrollLinearLayoutManager(context)
            overScrollMode = OVER_SCROLL_NEVER
        }
        categoryRecycler.adapter = adapter
        slidingPanel?.setScrollableView(categoryRecycler)
    }

    fun setParentSlidingPanel(slidingPanel: SlidingUpPanelLayout?) {
        this.slidingPanel = slidingPanel?.apply {
            addPanelSlideListener(onSlideListener)
        }
    }

    private fun collapseAllCategories() {
        adapter?.collapseAll()
    }

    private val onSlideListener = object : SlidingUpPanelLayout.SimplePanelSlideListener() {
        override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
            if (newState == PanelState.HIDDEN || newState == PanelState.COLLAPSED) {
                collapseAllCategories()
            }
        }
    }

    companion object {
        fun instance() = AppsFragment()
    }
}