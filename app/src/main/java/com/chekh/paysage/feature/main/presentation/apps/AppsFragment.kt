package com.chekh.paysage.feature.main.presentation.apps

import android.os.Bundle
import android.view.View
import android.view.View.OVER_SCROLL_NEVER
import android.view.WindowInsets
import androidx.core.view.marginBottom
import androidx.core.view.marginTop
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.applyPadding
import com.chekh.paysage.core.extension.setHeight
import com.chekh.paysage.core.extension.setMarginBottom
import com.chekh.paysage.core.extension.setMarginTop
import com.chekh.paysage.core.handler.slide.DockBarSlideHandler
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.tools.hideKeyboard
import com.chekh.paysage.feature.main.presentation.apps.adapter.AppsCategoryAdapter
import com.chekh.slidinguppanel.SlidingUpPanelLayout
import com.chekh.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.chekh.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.chekh.slidinguppanel.isClosed
import com.chekh.slidinguppanel.isOpened
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_apps.*

@AndroidEntryPoint
class AppsFragment : BaseFragment(R.layout.fragment_apps), PanelSlideListener {

    private val viewModel: AppsViewModel by viewModels()

    private val adapter: AppsCategoryAdapter by lazy {
        AppsCategoryAdapter(
            viewModel::toggleCategory,
            viewModel::scrollCategoryOffset,
            this::scrollStateChanged
        )
    }

    private var slidingPanel: SlidingUpPanelLayout? = null

    private val dockBarSlideHandler by lazy {
        DockBarSlideHandler(dbvApps, oflPanel)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupParentSlidingPanel(view)
        initViewModel()
    }

    private fun initViewModel() {
        viewModel.init(Unit)

        viewModel.scrollPositionLiveData.observe(viewLifecycleOwner) { position ->
            srvCategories.smoothScrollToPosition(position)
        }
        viewModel.appsGroupByCategoriesLiveData.observe(viewLifecycleOwner) { categories ->
            adapter.setAppsCategories(categories)
        }
        viewModel.dockAppsLiveData.observe(viewLifecycleOwner) { dockApps ->
            dbvApps.setApps(dockApps)
            dbvApps.setHeight(dockApps.settings.appSize)
            recalculateDockBounds()
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val smallDimen = resources.getDimension(R.dimen.small).toInt()
        val height = insets.systemWindowInsetBottom
        srvCategories.applyPadding(bottom = smallDimen + height)
        if (slidingPanel?.isOpened == false) {
            dbvApps.setMarginBottom(smallDimen + height)
            recalculateDockBounds()
        }
    }

    private fun setupParentSlidingPanel(view: View) {
        slidingPanel = view.parent?.parent as? SlidingUpPanelLayout
        slidingPanel?.addPanelSlideListener(this)
    }

    private fun setupListView() {
        srvCategories.overScrollMode = OVER_SCROLL_NEVER
        srvCategories.adapter = adapter
        slidingPanel?.setScrollableView(srvCategories)
    }

    private fun scrollStateChanged(state: Int) {
        slidingPanel?.isTouchEnabled = state != RecyclerView.SCROLL_STATE_DRAGGING
    }

    private fun recalculateDockBounds() {
        val height = dbvApps.layoutParams.height
        val absoluteHeight = height + dbvApps.marginTop + dbvApps.marginBottom
        slidingPanel?.panelHeight = absoluteHeight

        if (slidingPanel?.isClosed == true) {
            oflPanel.setMarginTop(absoluteHeight)
        }
    }

    override fun onPanelStateChanged(panel: View, previousState: PanelState, newState: PanelState) {
        view?.hideKeyboard()
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
