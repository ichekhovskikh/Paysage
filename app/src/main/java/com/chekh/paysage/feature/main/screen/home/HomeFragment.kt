package com.chekh.paysage.feature.main.screen.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowInsets
import androidx.core.view.marginTop
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.inTransaction
import com.chekh.paysage.core.extension.setMarginTop
import com.chekh.paysage.core.handler.backpressed.BackPressedHandler
import com.chekh.paysage.core.handler.backpressed.SlidingPanelBackPressedHandler
import com.chekh.paysage.core.handler.slide.SearchBarSlideHandler
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.core.ui.tools.MetricsConverter
import com.chekh.paysage.feature.main.screen.apps.AppsFragment
import com.chekh.paysage.feature.main.screen.desktop.DesktopFragment
import com.chekh.slidinguppanel.SlidingUpPanelLayout
import javax.inject.Inject
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment :
    BaseFragment(
        R.layout.fragment_home
    ),
    SlidingUpPanelLayout.PanelSlideListener {

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    @Inject
    lateinit var metricsConverter: MetricsConverter

    private val backPressedHandler: BackPressedHandler by lazy {
        SlidingPanelBackPressedHandler(suplSlidingPanel, childFragmentManager)
    }

    private val searchBarSlideHandler by lazy {
        SearchBarSlideHandler(msbSearch)
    }

    private val searchHeight: Int
        get() {
            msbSearch.measure(0, WRAP_CONTENT)
            return msbSearch.measuredHeight + msbSearch.paddingBottom + msbSearch.marginTop
        }

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
        suplSlidingPanel.anchorPoint = 1 - metricsConverter.pxToPercentage(searchHeight)
        suplSlidingPanel.addPanelSlideListener(this)
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
        val activity = activity ?: return
        val isDark = slideOffset >= suplSlidingPanel.anchorPoint - 0.1
        statusBarDecorator.statusBarDarkMode(activity, isDark)
        searchBarSlideHandler.slideToTop(transformOffset(slideOffset))
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
