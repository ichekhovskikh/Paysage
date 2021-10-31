package com.chekh.paysage.feature.main.presentation.options

import android.graphics.RectF
import android.os.Bundle
import android.transition.Fade
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.backpressed.ActionBeforeBackPressedHandler
import com.chekh.paysage.core.handler.backpressed.BackPressedHandler
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.apps.AppDockViewModel
import com.chekh.paysage.feature.main.presentation.options.anim.DesktopOptionsOverlayAnimationFacade
import com.chekh.paysage.feature.widget.presentation.widgetboard.WidgetBoardFragment
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import kotlinx.android.synthetic.main.fragment_desktop_options.*

@AndroidEntryPoint
class DesktopOptionsFragment :
    BaseFragment(R.layout.fragment_desktop_options),
    DragAndDropListener {

    private val appDockViewModel: AppDockViewModel by activityViewModels()

    private val overlayAnimation by lazyUnsafe {
        DesktopOptionsOverlayAnimationFacade(flBackground, tvWallpaper, tvWidgets, tvSettings)
    }

    private val backPressedHandler: BackPressedHandler by lazyUnsafe {
        ActionBeforeBackPressedHandler(this, overlayAnimation::reverse)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyWindowInsets()
        setupBackground()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()
        overlayAnimation.start()
    }

    override fun onStop() {
        super.onStop()
        overlayAnimation.cancel()
    }

    private fun setupBackground() {
        flBackground.setBackgroundResource(R.drawable.bg_black_gradient)
    }

    private fun setupListeners() {
        val activity = activity as? DesktopActivity
        activity?.addDragAndDropListener(this)

        overlayAnimation.doOnEnd { isReverse ->
            appDockViewModel.isAppDockVisible.value = isReverse
            if (isReverse) exit()
        }

        flBackground.onClick(backPressedHandler::onBackPressed)
        tvWallpaper.onVibrateClick {
            // TODO open Wallpaper Screen
        }
        tvWidgets.onVibrateClick {
            val fragment = WidgetBoardFragment()
            fragment.enterTransition = Fade()
            fragment.exitTransition = Fade()
            parentFragmentManager.commit {
                addWithBackStack(R.id.fcvContainer, fragment)
            }
        }
        tvSettings.onVibrateClick {
            // TODO open Settings Screen
        }
    }

    private fun applyWindowInsets() {
        llButtons.applyInsetter {
            type(navigationBars = true) {
                margin(animated = true)
            }
        }
    }

    override fun onDragStart(location: RectF, data: ClipData?) = exit()

    override fun onBackPressed() = backPressedHandler.onBackPressed()
}
