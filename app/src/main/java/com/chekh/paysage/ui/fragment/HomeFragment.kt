package com.chekh.paysage.ui.fragment

import android.os.Bundle
import android.view.View
import com.chekh.paysage.R
import com.chekh.paysage.ui.addStatusBarMarginTop
import com.chekh.paysage.ui.handler.SearchBarSlideHandler
import kotlinx.android.synthetic.main.fragment_home.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.chekh.paysage.ui.inTransaction
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.fragment_apps.*

class HomeFragment : BaseFragment() {

    private lateinit var desktopFragment: DesktopFragment
    private lateinit var appsFragment: AppsFragment

    override fun getLayoutId() = R.layout.fragment_home

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
            if (slideOffset >= slidingPanel.anchorPoint) {
                //TODO change status icon color dark
            } else {
                //TODO change status icon color light
            }
            searchBarSlideHandler.slideFromTop(transformOffset(slideOffset))
        }

        private fun transformOffset(offset: Float): Float {
            val anchor = slidingPanel.anchorPoint
            return if (offset > anchor) (1 - offset) / (1 - anchor) else offset / anchor
        }
    }

    companion object {
        fun instance() = HomeFragment()
    }
}