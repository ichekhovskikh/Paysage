package com.chekh.paysage.feature.home

import android.os.Bundle
import android.view.View
import androidx.core.view.marginTop
import com.chekh.paysage.R
import com.chekh.paysage.extension.inTransaction
import com.chekh.paysage.handler.SearchBarSlideHandler
import com.chekh.paysage.ui.fragment.ViewModelFragment
import com.chekh.paysage.handler.backpressed.SlidingPanelBackPressedHandler
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.extension.observe
import com.chekh.paysage.extension.setMarginTop
import com.chekh.paysage.feature.home.apps.fragment.AppsFragment
import com.chekh.paysage.feature.home.desktop.fragment.DesktopFragment
import com.chekh.paysage.handler.backpressed.BackPressedHandler
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : ViewModelFragment<HomeViewModel>() {

    private val backPressedHandler: BackPressedHandler by lazy {
        SlidingPanelBackPressedHandler(slidingPanel, childFragmentManager)
    }

    private val statusBarDecorator: StatusBarDecorator by lazy { CommonStatusBarDecorator() }

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
        viewModel.windowInsets.observe(this) { insets ->
            val height = insets.systemWindowInsetTop
            searchBar.setMarginTop(defaultSearchMarginTop + height)
            slideable.setMarginTop(defaultSlideableMarginTop + height)
        }
    }

    private fun setupSlidingPanel() {
        val desktopFragment = DesktopFragment.instance()
        val appsFragment = AppsFragment.instance()
        childFragmentManager.inTransaction {
            replace(R.id.main, desktopFragment)
            replace(R.id.slideable, appsFragment)
        }
        appsFragment.setParentSlidingPanel(slidingPanel)
        slidingPanel.addPanelSlideListener(onSlideListener)
    }

    private val onSlideListener = object : SlidingUpPanelLayout.SimplePanelSlideListener() {

        private val searchBarSlideHandler by lazy { SearchBarSlideHandler(searchBar) }

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            if (slideOffset >= slidingPanel.anchorPoint - 0.1) {
                activity?.let { statusBarDecorator.statusBarDarkMode(it, true) }
            } else {
                activity?.let { statusBarDecorator.statusBarDarkMode(it, false) }
            }
            searchBarSlideHandler.slideFromTop(transformOffset(slideOffset))
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