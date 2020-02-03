package com.chekh.paysage.feature.home

import android.os.Bundle
import android.view.View
import androidx.core.view.marginTop
import com.chekh.paysage.R
import com.chekh.paysage.extension.inTransaction
import com.chekh.paysage.handler.SearchBarSlideHandler
import com.chekh.paysage.ui.fragment.ViewModelFragment
import com.chekh.paysage.handler.SlidingPanelBackPressedHandler
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout.PanelState
import com.chekh.paysage.extension.observe
import com.chekh.paysage.extension.setMarginTop
import com.chekh.paysage.extension.statusDarkBarMode
import com.chekh.paysage.feature.home.apps.fragment.AppsFragment
import com.chekh.paysage.feature.home.desktop.fragment.DesktopFragment
import com.chekh.paysage.handler.BackPressedHandler
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : ViewModelFragment<HomeViewModel>() {

    private var desktopFragment: DesktopFragment? = null
    private var appsFragment: AppsFragment? = null

    private val backPressedHandler: BackPressedHandler by lazy {
        SlidingPanelBackPressedHandler(slidingPanel, childFragmentManager)
    }

    override val layoutId = R.layout.fragment_home
    override val viewModelClass = HomeViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSlidingPanel()
    }

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        super.onViewModelCreated(savedInstanceState)
        val defaultSearchMarginTop = searchBar.marginTop
        val defaultSlideableMarginTop = slideable.marginTop
        viewModel.statusBarHeightLiveData.observe(this) { height ->
            searchBar.setMarginTop(defaultSearchMarginTop + height)
            slideable.setMarginTop(defaultSlideableMarginTop + height)
        }
    }

    private fun setupSlidingPanel() {
        initFragmentsIfNeed()
        childFragmentManager.inTransaction {
            replace(R.id.main, desktopFragment!!)
            replace(R.id.slideable, appsFragment!!)
        }
        slidingPanel.addPanelSlideListener(onSlideListener)
    }

    private fun initFragmentsIfNeed() {
        if (desktopFragment == null) {
            desktopFragment = DesktopFragment.instance()
        }
        if (appsFragment == null) {
            appsFragment = AppsFragment.instance()
            appsFragment?.setOnRecyclerCreatedListener { slidingPanel.setScrollableView(it) }
        }
    }

    private val onSlideListener = object : SlidingUpPanelLayout.SimplePanelSlideListener() {

        private val searchBarSlideHandler by lazy { SearchBarSlideHandler(searchBar) }

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            if (slideOffset >= slidingPanel.anchorPoint - 0.1) {
                activity?.statusDarkBarMode(true)
            } else {
                activity?.statusDarkBarMode(false)
            }
            searchBarSlideHandler.slideFromTop(transformOffset(slideOffset))
        }

        override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
            if (newState == PanelState.HIDDEN || newState == PanelState.COLLAPSED) {
                appsFragment?.collapseAllCategories()
            }
        }

        private fun transformOffset(offset: Float): Float {
            val anchor = slidingPanel.anchorPoint
            return if (offset > anchor) (1 - offset) / (1 - anchor) else offset / anchor
        }
    }

    override fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }

    companion object {
        fun instance() = HomeFragment()
    }
}