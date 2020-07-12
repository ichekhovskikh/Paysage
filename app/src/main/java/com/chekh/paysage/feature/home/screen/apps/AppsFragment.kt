package com.chekh.paysage.feature.home.screen.apps

import android.os.Bundle
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.WindowInsets
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.extension.applyPadding
import com.chekh.paysage.extension.get
import com.chekh.paysage.extension.observe
import com.chekh.paysage.feature.home.screen.apps.adapter.AppsCategoryAdapter
import com.chekh.paysage.handler.slide.DockBarSlideHandler
import com.chekh.paysage.ui.fragment.ViewModelFragment
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import kotlinx.android.synthetic.main.fragment_apps.*

class AppsFragment : ViewModelFragment<AppsViewModel>(), PanelSlideListener {

    override val layoutId = R.layout.fragment_apps
    override val viewModelClass = AppsViewModel::class

    private val dockAppsViewModel by lazy {
        viewModelFactory.get(this, DockAppsViewModel::class)
    }

    private val adapter: AppsCategoryAdapter by lazy {
        AppsCategoryAdapter(
            viewModel::toggleCategory,
            viewModel::scrollCategoryOffset,
            ::scrollStateChanged
        )
    }

    private var slidingPanel: SlidingUpPanelLayout? = null

    private val dockBarSlideHandler by lazy {
        DockBarSlideHandler(dbvApps)
    }

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
        dockAppsViewModel.init(Unit)

        viewModel.scrollPositionLiveData.observe(this) { position ->
            srvCategories.smoothScrollToPosition(position)
        }
        viewModel.appsGroupByCategoriesLiveData.observe(this) { categories ->
            adapter.setAppsCategories(categories)
        }
        dockAppsViewModel.dockAppsLiveData.observe(this) { appList ->
            dbvApps.setApps(appList)
            updateDockSize(appList.appSize)
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

    private fun scrollStateChanged(state: Int) {
        slidingPanel?.isTouchEnabled = state != RecyclerView.SCROLL_STATE_DRAGGING
    }

    private fun updateDockSize(size: Int) {
        val dockLayoutParams = dbvApps.layoutParams
        val padding = resources.getDimensionPixelSize(R.dimen.micro)
        val height = size + padding * 2
        if (height == dockLayoutParams.height) return

        dockLayoutParams.height = height
        dbvApps.layoutParams = dockLayoutParams

        dockBarSlideHandler.viewHeightChanged()
        val margin = resources.getDimensionPixelSize(R.dimen.small)
        slidingPanel?.panelHeight = height + margin * 2
    }

    override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
        if (newState in listOf(PanelState.HIDDEN, PanelState.COLLAPSED)) {
            viewModel.collapseAll()
        }
    }

    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        val anchorPoint = slidingPanel?.anchorPoint ?: return
        if (slideOffset <= anchorPoint) {
            dockBarSlideHandler.slideToTop(slideOffset / anchorPoint)
        }
    }
}