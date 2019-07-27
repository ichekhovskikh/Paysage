package com.chekh.paysage.ui.fragment

import android.os.Bundle
import android.view.View
import com.chekh.paysage.R
import com.chekh.paysage.ui.util.addStatusBarMarginTop
import com.chekh.paysage.ui.handler.SearchBarSlideHandler
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.chekh.paysage.ui.handler.SlidingPanelBackPressedHandler
import com.chekh.paysage.ui.util.inTransaction
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_apps.*
import com.chekh.paysage.ui.util.statusDarkBarMode

class HomeFragment : BaseFragment() {

    private lateinit var desktopFragment: DesktopFragment
    private lateinit var appsFragment: AppsFragment
    private lateinit var backPressedHandler: SlidingPanelBackPressedHandler

    override val layoutId = R.layout.fragment_home

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupSlidingPanel()
        addStatusBarMarginTop(searchBar)
    }

    private fun setupSlidingPanel() {
        initializeFragmentsIfNeed()
        setScrollableViewWhenBeCreated()
        addStatusBarMarginTop(slideable)
        childFragmentManager.inTransaction {
            replace(R.id.main, desktopFragment)
            replace(R.id.slideable, appsFragment)
        }
        slidingPanel.addPanelSlideListener(onSlideListener)
        backPressedHandler = SlidingPanelBackPressedHandler(slidingPanel)
    }

    private fun initializeFragmentsIfNeed() {
        if (!::desktopFragment.isInitialized) {
            desktopFragment = DesktopFragment.instance()
        }
        if (!::appsFragment.isInitialized) {
            appsFragment = AppsFragment.instance()
        }
    }

    private fun setScrollableViewWhenBeCreated() {
        appsFragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStart() {
                slidingPanel.setScrollableView(appsFragment.categoryRecycler)
                appsFragment.lifecycle.removeObserver(this)
            }
        })
    }

    private val onSlideListener = object : SlidingUpPanelLayout.SimplePanelSlideListener() {

        private val searchBarSlideHandler by lazy { SearchBarSlideHandler(searchBar) }

        override fun onPanelSlide(panel: View?, slideOffset: Float) {
            if (slideOffset >= slidingPanel.anchorPoint - 0.1) {
                activity?.statusDarkBarMode(true)
            } else {
                activity?.statusDarkBarMode(false)
            }
            searchBarSlideHandler.slideFromTop(transformOffset(slideOffset))
        }

        private fun transformOffset(offset: Float): Float {
            val anchor = slidingPanel.anchorPoint
            return if (offset > anchor) (1 - offset) / (1 - anchor) else offset / anchor
        }
    }

    override fun onBackPressed(): Boolean {
        return if (::backPressedHandler.isInitialized) backPressedHandler.onBackPressed() else super.onBackPressed()
    }

    companion object {
        fun instance() = HomeFragment()
    }
}