package com.chekh.paysage.feature.main.presentation.options

import android.graphics.RectF
import android.os.Bundle
import android.transition.Fade
import android.view.View
import android.view.WindowInsets
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.DesktopInsetsViewModel
import com.chekh.paysage.feature.main.presentation.apps.AppDockViewModel
import com.chekh.paysage.feature.main.presentation.options.anim.DesktopOptionsOverlayAnimationFacade
import com.chekh.paysage.feature.widget.presentation.widgetboard.WidgetBoardFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_desktop_options.*

@AndroidEntryPoint
class DesktopOptionsFragment :
    BaseFragment(R.layout.fragment_desktop_options),
    DragAndDropListener {

    private val appDockViewModel: AppDockViewModel by activityViewModels()
    private val insetsViewModel: DesktopInsetsViewModel by activityViewModels()

    private val overlayAnimationFacade by lazyUnsafe {
        DesktopOptionsOverlayAnimationFacade(flBackground, tvWallpaper, tvWidgets, tvSettings)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackground()
        setupListeners()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        startOverlayAnimation(isReverse = false)
    }

    private fun setupBackground() {
        flBackground.setBackgroundResource(R.drawable.background_black_gradient)
    }

    private fun setupListeners() {
        val activity = activity as? DesktopActivity
        activity?.addDragAndDropListener(this)

        overlayAnimationFacade.doOnEnd { isReverse ->
            appDockViewModel.isAppDockVisible.value = isReverse
            if (isReverse) exit()
        }

        flBackground.onClick {
            startOverlayAnimation(isReverse = true)
        }
        tvWallpaper.onVibrateClick {
            // TODO open Wallpaper Screen
        }
        tvWidgets.onVibrateClick {
            val fragment = WidgetBoardFragment()
            fragment.enterTransition = Fade()
            fragment.exitTransition = Fade()
            parentFragmentManager.commit {
                addWithBackStack(R.id.flContainer, fragment)
            }
        }
        tvSettings.onVibrateClick {
            // TODO open Settings Screen
        }
    }

    private fun setupViewModel() {
        insetsViewModel.windowInsets.observe(viewLifecycleOwner, ::onApplyWindowInsets)
    }

    @Suppress("DEPRECATION")
    private fun onApplyWindowInsets(insets: WindowInsets) {
        llButtons.bottomMargin = insets.systemWindowInsetBottom
    }

    private fun startOverlayAnimation(isReverse: Boolean) {
        overlayAnimationFacade.start(isReverse)
    }

    override fun onDragStart(location: RectF, data: ClipData?) = exit()

    override fun onBackPressed(): Boolean {
        val lastFragment = parentFragmentManager.fragments.lastOrNull()
        if (this === lastFragment) {
            startOverlayAnimation(isReverse = true)
            return true
        }
        return super.onBackPressed()
    }
}
