package com.chekh.paysage.feature.home.screen.apps

import android.os.Bundle
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.WindowInsets
import com.chekh.paysage.R
import com.chekh.paysage.extension.applyPadding
import com.chekh.paysage.extension.observe
import com.chekh.paysage.feature.home.screen.apps.adapter.AppsCategoryAdapter
import com.chekh.paysage.ui.fragment.ViewModelFragment
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout.PanelSlideListener
import com.chekh.paysage.ui.view.slidingpanel.SlidingUpPanelLayout.PanelState
import kotlinx.android.synthetic.main.fragment_apps.*

class AppsFragment : ViewModelFragment<AppsViewModel>(), PanelSlideListener {

    override val layoutId = R.layout.fragment_apps
    override val viewModelClass = AppsViewModel::class

    private val adapter: AppsCategoryAdapter by lazy {
        AppsCategoryAdapter(viewModel::toggleCategory, viewModel::scrollCategoryOffset)
    }

    private var slidingPanel: SlidingUpPanelLayout? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupRecycler()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupParentSlidingPanel(view)
    }

    override fun onViewModelCreated(savedInstanceState: Bundle?) {
        super.onViewModelCreated(savedInstanceState)
        viewModel.init(Unit)

        viewModel.scrollPositionLiveData.observe(this) { position ->
            srvCategories.smoothScrollToPosition(position)
        }
        viewModel.appsGroupByCategoriesLiveData.observe(this) { categories ->
            adapter.setAppsCategories(categories)
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val defaultPaddingBottom = resources.getDimension(R.dimen.small).toInt()
        val height = insets.systemWindowInsetBottom
        srvCategories.applyPadding(bottom = defaultPaddingBottom + height)
    }

    private fun setupParentSlidingPanel(view: View) {
        slidingPanel = view.parent?.parent as? SlidingUpPanelLayout
        slidingPanel?.addPanelSlideListener(this)
    }

    private fun setupRecycler() {
        srvCategories.overScrollMode = OVER_SCROLL_NEVER
        srvCategories.adapter = adapter
        slidingPanel?.setScrollableView(srvCategories)
    }

    override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
        if (newState in listOf(PanelState.HIDDEN, PanelState.COLLAPSED)) {
            viewModel.collapseAll()
        }
    }

    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        // Nothing
    }
}