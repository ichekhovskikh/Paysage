package com.chekh.paysage.feature.home

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import com.chekh.paysage.R
import com.chekh.paysage.extension.inTransaction
import com.chekh.paysage.handler.SearchBarSlideHandler
import com.chekh.paysage.handler.backpressed.SlidingPanelBackPressedHandler
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout.PanelState
import com.chekh.paysage.extension.setMarginTop
import com.chekh.paysage.feature.home.screen.apps.fragment.AppsFragment
import com.chekh.paysage.feature.home.screen.desktop.fragment.DesktopFragment
import com.chekh.paysage.handler.backpressed.BackPressedHandler
import com.chekh.paysage.ui.fragment.BaseFragment
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment(), SlidingUpPanelLayout.PanelSlideListener {

    private val backPressedHandler: BackPressedHandler by lazy {
        SlidingPanelBackPressedHandler(suplSlidingPanel, childFragmentManager)
    }

    private val statusBarDecorator: StatusBarDecorator by lazy { CommonStatusBarDecorator() }
    private val searchBarSlideHandler by lazy { SearchBarSlideHandler(msbSearch) }

    override val layoutId = R.layout.fragment_home

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSlidingPanel()
    }

    private fun setupSlidingPanel() {
        val desktopFragment = DesktopFragment()
        val appsFragment = AppsFragment()
        childFragmentManager.inTransaction {
            replace(R.id.flDesktop, desktopFragment)
            replace(R.id.flApps, appsFragment)
        }
        suplSlidingPanel.addPanelSlideListener(this)
    }

    override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
        // nothing
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
        val activity = activity ?: return
        val isDark = slideOffset >= suplSlidingPanel.anchorPoint - 0.1
        statusBarDecorator.statusBarDarkMode(activity, isDark)
        searchBarSlideHandler.slideFromTop(transformOffset(slideOffset))
    }

    private fun transformOffset(offset: Float): Float {
        val anchor = suplSlidingPanel.anchorPoint
        return when (offset > anchor) {
            true -> (1 - offset) / (1 - anchor)
            else -> offset / anchor
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val searchMarginTop = resources.getDimension(R.dimen.search_bar_margin_top).toInt()
        val height = insets.systemWindowInsetTop
        msbSearch.setMarginTop(searchMarginTop + height)
        flApps.setMarginTop(height)
    }

    override fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }
}