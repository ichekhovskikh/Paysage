package com.chekh.paysage.feature.main.home.presentation

import android.graphics.RectF
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.chekh.paysage.R
import com.chekh.paysage.core.ui.activity.BaseActivity
import com.chekh.paysage.core.ui.statusbar.StatusBarDecorator
import com.chekh.paysage.core.ui.tools.Size
import com.chekh.paysage.core.ui.view.drag.ClipData
import com.chekh.paysage.core.ui.view.drag.DragAndDropListener
import com.chekh.paysage.core.ui.view.drag.DragResizeListener
import com.chekh.paysage.core.extension.applyWindowInsets
import com.chekh.paysage.core.extension.navigationBar
import com.chekh.paysage.core.extension.statusBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity(R.layout.activity_main) {

    private val homeViewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var statusBarDecorator: StatusBarDecorator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        statusBarDecorator.setTransparentStatusBar(this)
        applyWindowInsets { setBarShadow(it.statusBar, it.navigationBar) }
        addHomeFragmentIfNeed()
        setupViewModel()
    }

    private fun setupViewModel() {
        homeViewModel.init(Unit)
    }

    private fun addHomeFragmentIfNeed() {
        if (supportFragmentManager.backStackEntryCount > 0) return
        val homeFragment = HomeFragment()
        supportFragmentManager.commit {
            add(R.id.fcvContainer, homeFragment)
        }
    }

    private fun setBarShadow(statusShadowHeight: Int, navigationShadowHeight: Int) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            swStatusBarShadow.shadowHeight = statusShadowHeight
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            swNavigationBarShadow.shadowHeight = navigationShadowHeight
        }
    }

    fun startDragAndDrop(view: View, data: ClipData? = null) {
        dllContent.startDragAndDrop(view, data)
    }

    fun startDragResize(
        view: View,
        minSize: Size? = null,
        maxSize: Size? = null,
        data: ClipData? = null
    ) {
        dllContent.startDragResize(view, minSize, maxSize, data)
    }

    fun stopDragAndDrop() {
        dllContent.stopDragAndDrop()
    }

    fun stopDragResize() {
        dllContent.stopDragResize()
    }

    fun setTargetDragViewBounds(bounds: RectF?) {
        dllContent.setTargetDragViewBounds(bounds)
    }

    fun addDragAndDropListener(listener: DragAndDropListener) {
        dllContent.addDragAndDropListener(listener)
    }

    fun addDragResizeListener(listener: DragResizeListener) {
        dllContent.addDragResizeListener(listener)
    }
}
