package com.chekh.paysage.ui.fragment

import android.os.Bundle
import android.view.View
import com.chekh.paysage.R
import com.chekh.paysage.ui.handler.SearchBarSlideHandler
import com.chekh.paysage.ui.fragment.core.ViewModelFragment
import com.chekh.paysage.ui.handler.SlidingPanelBackPressedHandler
import com.chekh.paysage.ui.util.*
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : ViewModelFragment<HomeViewModel>() {

    private lateinit var desktopFragment: DesktopFragment
    private lateinit var appsFragment: AppsFragment
    private lateinit var backPressedHandler: SlidingPanelBackPressedHandler

    override val layoutId = R.layout.fragment_home
    override val viewModelClass = HomeViewModel::class.java

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSlidingPanel()
    }

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        super.onViewModelCreated(savedInstanceState)
        val defaultSearchMarginTop = searchBar.getMarginTop()
        val defaultSlideableMarginTop = slideable.getMarginTop()
        viewModel.statusBarHeightLiveData.observe(this) { height ->
            searchBar.setMarginTop(defaultSearchMarginTop + height)
            slideable.setMarginTop(defaultSlideableMarginTop + height)
        }
    }

    private fun setupSlidingPanel() {
        initFragmentsIfNeed()
        childFragmentManager.inTransaction {
            replace(R.id.main, desktopFragment)
            replace(R.id.slideable, appsFragment)
        }
        slidingPanel.addPanelSlideListener(onSlideListener)
        backPressedHandler = SlidingPanelBackPressedHandler(slidingPanel)
    }

    private fun initFragmentsIfNeed() {
        if (!::desktopFragment.isInitialized) {
            desktopFragment = DesktopFragment.instance()
        }
        if (!::appsFragment.isInitialized) {
            appsFragment = AppsFragment.instance()
            appsFragment.setOnRecyclerCreatedListener { slidingPanel.setScrollableView(it) }
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

        private fun transformOffset(offset: Float): Float {
            val anchor = slidingPanel.anchorPoint
            return if (offset > anchor) (1 - offset) / (1 - anchor) else offset / anchor
        }
    }

    override fun onBackPressed(): Boolean {
        return if (::backPressedHandler.isInitialized) backPressedHandler.onBackPressed() else super.onBackPressed()
    }

    companion object {
        fun instance() = HomeFragment()
    }
}