package com.chekh.paysage.feature.main.presentation.home

import android.graphics.RectF
import android.os.Bundle
import android.view.View
import androidx.core.view.marginTop
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.common.data.model.DesktopWidgetType
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.slide.SearchBarSlideHandler
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.BottomSheetCallback
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.from
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.apps.AppDockViewModel
import com.chekh.paysage.feature.main.presentation.apps.AppsFragment
import com.chekh.paysage.feature.main.presentation.pager.DesktopPagerFragment
import com.chekh.paysage.feature.widget.presentation.widgetboard.data.WidgetClipData
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment(R.layout.fragment_home),
    BottomSheetCallback,
    DragAndDropListener {

    private val appDockViewModel: AppDockViewModel by activityViewModels()

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    private val bottomSheetBehavior by lazyUnsafe { from(svBottomSheet) }

    private val searchBarSlideHandler by lazyUnsafe {
        SearchBarSlideHandler(msbSearch)
    }

    private val searchHeight: Int
        get() {
            msbSearch.measure()
            return msbSearch.measuredHeight + 3 * msbSearch.marginTop
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyWindowInsets()
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
        appDockViewModel.isAppDockVisible.observe(viewLifecycleOwner, ::setAppDockVisible)
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        val activity = activity ?: return
        val isDark = slideOffset >= bottomSheetBehavior.halfExpandedRatio - 0.1
        statusBarDecorator.statusBarDarkMode(activity, isDark)

        if (flDesktops.foreground == null) {
            flDesktops.setForegroundColorResource(colorRes = R.color.white, alpha = 0)
        }
        if (slideOffset >= 0) {
            flDesktops.foreground.alpha = slideOffset.toAlpha()
        }
        val anchor = bottomSheetBehavior.halfExpandedRatio
        searchBarSlideHandler.slideToTop(slideOffset, anchor)
    }

    override fun onDragStart(location: RectF, data: ClipData?) {
        val type = (data as? WidgetClipData)?.type
        setAppDockVisible(type != DesktopWidgetType.WIDGET)
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        setAppDockVisible(true)
    }

    private fun applyWindowInsets() {
        msbSearch.applyInsetter {
            type(statusBars = true) {
                margin(animated = true)
            }
        }
    }

    private fun setAppDockVisible(isVisible: Boolean) {
        bottomSheetBehavior.isHideable = !isVisible
    }
}
