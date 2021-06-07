package com.chekh.paysage.feature.main.presentation.options

import android.app.WallpaperManager
import android.graphics.RectF
import android.os.Bundle
import android.transition.*
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.tools.*
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.DesktopInsetsViewModel
import com.chekh.paysage.feature.main.presentation.options.anim.DesktopOptionsOverlayAnimationFacade
import com.chekh.paysage.feature.widget.presentation.widgetboard.WidgetBoardFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_desktop_options.*
import javax.inject.Inject

@AndroidEntryPoint
class DesktopOptionsFragment :
    BaseFragment(R.layout.fragment_desktop_options),
    DragAndDropListener {

    private val insetsViewModel: DesktopInsetsViewModel by activityViewModels()

    @Inject
    lateinit var wallpaperManager: WallpaperManager

    private val overlayAnimationFacade by lazy {
        DesktopOptionsOverlayAnimationFacade(flBackground, tvWallpaper, tvWidgets, tvSettings)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBackgroundBlur()
        setupListeners()
        setupViewModel()
    }

    override fun onStart() {
        super.onStart()
        startOverlayAnimation(isReverse = false)
    }

    private fun setupBackgroundBlur() {
        val view = view?.parent as? ViewGroup ?: return
        val background = listOf(
            wallpaperManager.drawable.toBitmap(),
            view.makeBitmapScreenshot()
        )
            .combine()
            .blur(view.context, BACKGROUND_BLUR_SCALE, BACKGROUND_BLUR_RADIUS)
            .darken()

        flBackground.setBackgroundBitmap(background)
    }

    private fun setupListeners() {
        val activity = activity as? DesktopActivity
        activity?.addDragAndDropListener(this)
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
        overlayAnimationFacade.doOnEnd { isReverse ->
            if (isReverse) exit()
        }
    }

    private fun setupViewModel() {
        insetsViewModel.windowInsetsLiveData.observe(viewLifecycleOwner, ::onApplyWindowInsets)
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

    private companion object {
        const val BACKGROUND_BLUR_SCALE = 4f
        const val BACKGROUND_BLUR_RADIUS = 25f
    }
}
