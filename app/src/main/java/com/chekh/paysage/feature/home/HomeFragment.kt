package com.chekh.paysage.feature.home

import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import com.chekh.paysage.R
import com.chekh.paysage.extension.inTransaction
import com.chekh.paysage.handler.SearchBarSlideHandler
import com.chekh.paysage.ui.fragment.ViewModelFragment
import com.chekh.paysage.handler.backpressed.SlidingPanelBackPressedHandler
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.extension.setMarginTop
import com.chekh.paysage.feature.home.apps.fragment.AppsFragment
import com.chekh.paysage.feature.home.desktop.fragment.DesktopFragment
import com.chekh.paysage.handler.backpressed.BackPressedHandler
import com.chekh.paysage.ui.fragment.BaseFragment
import com.chekh.paysage.ui.statusbar.CommonStatusBarDecorator
import com.chekh.paysage.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.ui.util.MetricsConverter
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseFragment() {

    private val backPressedHandler: BackPressedHandler by lazy {
        SlidingPanelBackPressedHandler(slidingPanel, childFragmentManager)
    }

    private val statusBarDecorator: StatusBarDecorator by lazy { CommonStatusBarDecorator() }

    override val layoutId = R.layout.fragment_home

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSlidingPanel()
    }

    private fun setupSlidingPanel() {
        val desktopFragment = DesktopFragment.instance()
        val appsFragment = AppsFragment.instance()
        childFragmentManager.inTransaction {
            replace(R.id.desktop, desktopFragment)
            replace(R.id.apps, appsFragment)
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

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val context = context ?: return

        val searchMarginTop = MetricsConverter(context).dpToPx(18f)
        val height = insets.systemWindowInsetTop
        searchBar.setMarginTop(searchMarginTop + height)
        apps.setMarginTop(height)
    }

    override fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }

    companion object {
        fun instance() = HomeFragment()
    }
}