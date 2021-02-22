package com.chekh.paysage.feature.main.presentation.home

import android.graphics.RectF
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowInsets
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.slide.SearchBarSlideHandler
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.BottomSheetCallback
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.from
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.core.ui.tools.MetricsConverter
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.presentation.MainActivity
import com.chekh.paysage.feature.main.presentation.apps.AppsFragment
import com.chekh.paysage.feature.main.presentation.home.anim.OverlayHomeButtonsAnimationFacade
import com.chekh.paysage.feature.main.presentation.pager.DesktopPagerFragment
import com.chekh.paysage.feature.widget.presentation.widgetboard.WidgetBoardFragment
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_overlay_home_buttons.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment :
    BaseFragment(R.layout.fragment_home),
    BottomSheetCallback,
    DragAndDropListener {

    private val viewModel: HomeViewModel by viewModels(
        ownerProducer = { requireActivity() }
    )

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    @Inject
    lateinit var metricsConverter: MetricsConverter

    private val bottomSheetBehavior by lazy { from(svBottomSheet) }

    private val buttonsAnimationFacade by lazy {
        OverlayHomeButtonsAnimationFacade(blBackgroundBlur, tvWallpaper, tvWidgets, tvSettings)
    }

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
        setupBackgroundBlur()
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

    private fun setupBackgroundBlur() {
        val view = view as? ViewGroup ?: return
        blBackgroundBlur.setupWith(view)
            .setBlurEnabled(false)
            .setFrameClearDrawable(view.background)
            .setBlurAlgorithm(RenderScriptBlur(context))
            .setBlurRadius(BACKGROUND_BLUR_RADIUS)
            .setBlurAutoUpdate(true)
            .setHasFixedTransformationMatrix(true)
    }

    private fun setupListeners() {
        val activity = activity as? MainActivity
        activity?.addDragAndDropListener(this)
        blBackgroundBlur.onClick {
            setOverlayEnabledHomeButtons(false)
        }
        tvWallpaper.onVibrateClick {
            // TODO open Wallpaper Screen
        }
        tvWidgets.onVibrateClick {
            val fragment = WidgetBoardFragment()
            fragment.enterTransition = Fade()
            fragment.exitTransition = Fade()
            parentFragmentManager.commit {
                add(R.id.flContainer, fragment)
                addToBackStack(fragment::class.simpleName)
            }
        }
        tvSettings.onVibrateClick {
            // TODO open Settings Screen
        }
    }

    private fun setupViewModel() {
        viewModel.isEnabledOverlayHomeButtonsLiveData.observe(viewLifecycleOwner) { isEnabled ->
            setOverlayEnabledHomeButtons(isEnabled)
        }
    }

    private fun setOverlayEnabledHomeButtons(isEnabled: Boolean) {
        if (blBackgroundBlur.isVisible == isEnabled) return

        blBackgroundBlur.setBlurEnabled(isEnabled)
        blBackgroundBlur.isVisible = true

        buttonsAnimationFacade.cancel()
        buttonsAnimationFacade.start(!isEnabled)
        buttonsAnimationFacade.doOnEnd {
            bottomSheetBehavior.isHideable = isEnabled
            blBackgroundBlur.isVisible = isEnabled
        }
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
        blBackgroundBlur.setBlurEnabled(false)
        blBackgroundBlur.isVisible = false
    }

    override fun onDragEnd(location: RectF, data: ClipData?) {
        bottomSheetBehavior.state = CustomBottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior.isHideable = false
    }

    @Suppress("DEPRECATION")
    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val searchMarginTop = resources.getDimension(R.dimen.search_bar_margin_top).toInt()
        val insetTop = insets.systemWindowInsetTop
        val insetBottom = insets.systemWindowInsetBottom
        msbSearch.topMargin = searchMarginTop + insetTop
        llButtons.bottomMargin = insetBottom
    }

    override fun onBackPressed(): Boolean {
        val lastFragment = parentFragmentManager.fragments.lastOrNull()
        if (this === lastFragment && blBackgroundBlur.isVisible) {
            setOverlayEnabledHomeButtons(false)
            return true
        }
        return super.onBackPressed()
    }

    private companion object {
        const val BACKGROUND_BLUR_RADIUS = 8f
    }
}
