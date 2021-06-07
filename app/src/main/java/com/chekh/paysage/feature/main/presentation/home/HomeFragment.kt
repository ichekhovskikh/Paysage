package com.chekh.paysage.feature.main.presentation.home

import android.graphics.RectF
import android.os.Bundle
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowInsets
import androidx.core.view.marginTop
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.common.data.model.DesktopWidgetType
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.slide.SearchBarSlideHandler
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.BottomSheetCallback
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.from
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.DesktopInsetsViewModel
import com.chekh.paysage.feature.main.presentation.apps.AppsFragment
import com.chekh.paysage.feature.main.presentation.pager.DesktopPagerFragment
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment(R.layout.fragment_home),
    BottomSheetCallback,
    DragAndDropListener {

    private val insetsViewModel: DesktopInsetsViewModel by activityViewModels()

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    private val bottomSheetBehavior by lazy { from(svBottomSheet) }

    private val searchBarSlideHandler by lazy {
        SearchBarSlideHandler(msbSearch)
    }

    private val searchHeight: Int
        get() {
            msbSearch.measure(0, WRAP_CONTENT)
            return msbSearch.measuredHeight + 3 * msbSearch.marginTop
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSlidingPanel()
        setupListeners()
        setupViewModel()
    }

    private fun setupSlidingPanel() {
        if (childFragmentManager.backStackEntryCount > 0) return
        val desktopPagerFragment = DesktopPagerFragment()
        val appsFragment = AppsFragment()
        childFragmentManager.commit {
            replace(R.id.flDesktops, desktopPagerFragment)
            replace(R.id.flApps, appsFragment)
        }
        bottomSheetBehavior.halfExpandedOffset = searchHeight
        bottomSheetBehavior.addBottomSheetCallback(this)
    }

    private fun setupListeners() {
        val activity = activity as? DesktopActivity
        activity?.addDragAndDropListener(this)
    }

    private fun setupViewModel() {
        insetsViewModel.windowInsetsLiveData.observe(viewLifecycleOwner, ::onApplyWindowInsets)
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        val activity = activity ?: return
        val isDark = slideOffset >= bottomSheetBehavior.halfExpandedRatio - 0.1
        statusBarDecorator.statusBarDarkMode(activity, isDark)
        flDesktops.alpha = 1 - slideOffset

        val anchor = bottomSheetBehavior.halfExpandedRatio
        searchBarSlideHandler.slideToTop(slideOffset, anchor)
    }

    override fun onDragStart(location: RectF, data: ClipData?) {
        val type = (data as? WidgetClipData)?.type
        bottomSheetBehavior.isHideable = type == DesktopWidgetType.WIDGET
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        bottomSheetBehavior.state = CustomBottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isHideable = false
    }

    @Suppress("DEPRECATION")
    private fun onApplyWindowInsets(insets: WindowInsets) {
        val searchMarginTop = resources.getDimension(R.dimen.search_bar_margin_top).toInt()
        val insetTop = insets.systemWindowInsetTop
        msbSearch.topMargin = searchMarginTop + insetTop
    }
}
