package com.chekh.paysage.feature.main.presentation.home

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowInsets
import androidx.core.view.marginTop
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.slide.SearchBarSlideHandler
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.*
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.core.ui.tools.MetricsConverter
import com.chekh.paysage.feature.main.presentation.apps.AppsFragment
import com.chekh.paysage.feature.main.presentation.desktop.DesktopFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home), BottomSheetCallback {

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    @Inject
    lateinit var metricsConverter: MetricsConverter

    private val bottomSheetBehavior by lazy { from(svBottomSheet) }

    private val searchBarSlideHandler by lazy {
        SearchBarSlideHandler(msbSearch)
    }

    private val searchHeight: Int
        get() {
            msbSearch.measure(0, WRAP_CONTENT)
            return msbSearch.measuredHeight + 3 * msbSearch.marginTop
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
        bottomSheetBehavior.halfExpandedOffset = searchHeight
        bottomSheetBehavior.addBottomSheetCallback(this)
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        val activity = activity ?: return
        val isDark = slideOffset >= bottomSheetBehavior.halfExpandedRatio - 0.1
        statusBarDecorator.statusBarDarkMode(activity, isDark)
        flDesktop.alpha = 1 - slideOffset

        val anchor = bottomSheetBehavior.halfExpandedRatio
        searchBarSlideHandler.slideToTop(slideOffset, anchor)
    }

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val searchMarginTop = resources.getDimension(R.dimen.search_bar_margin_top).toInt()
        val insetTop = insets.systemWindowInsetTop
        msbSearch.setMarginTop(searchMarginTop + insetTop)
    }
}
