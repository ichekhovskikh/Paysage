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
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.slide.AppsBoardSlideHandler
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.tools.hideKeyboard
import com.chekh.paysage.feature.main.presentation.apps.adapter.AppsCategoryAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_HIDDEN
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_apps.*

@AndroidEntryPoint
class AppsFragment : BaseFragment(R.layout.fragment_apps), BottomSheetListener {

    private val viewModel: AppsViewModel by viewModels()

    private val adapter: AppsCategoryAdapter by lazy {
        AppsCategoryAdapter(viewModel::toggleCategory, viewModel::scrollCategoryOffset)
    }

    private var bottomSheetBehavior: BottomSheetBehavior<View>? = null

    private val appsBoardSlideHandler by lazy {
        AppsBoardSlideHandler(dbvApps, oclPanel)
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
            recalculatePeekBottomSheet()
        }
    }

    override fun onApplyWindowInsets(insets: WindowInsets) {
        super.onApplyWindowInsets(insets)
        val smallDimen = resources.getDimension(R.dimen.small).toInt()
        val insetBottom = insets.systemWindowInsetBottom
        val insetTop = insets.systemWindowInsetTop
        srvCategories.applyPadding(bottom = smallDimen + insetBottom)
        appsBoardSlideHandler.setExpandedMarginTop(insetTop)
        if (bottomSheetBehavior?.isOpened == false) {
            dbvApps.setMarginBottom(smallDimen + insetBottom)
            recalculatePeekBottomSheet()
        } else if (bottomSheetBehavior?.state == STATE_EXPANDED) {
            oclPanel.setMarginTop(insetTop)
        }
    }

    private fun setupParentSlidingPanel(view: View) {
        val parent = view.parent?.parent as? View ?: return
        bottomSheetBehavior = BottomSheetBehavior.from(parent)
        bottomSheetBehavior?.addBottomSheetListener(this)
    }

    private fun setupListView() {
        srvCategories.adapter = adapter
        srvCategories.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    val isOverTop = dy <= 0 && srvCategories.computeVerticalScrollOffset() <= 0
                    srvCategories.setBouncing(!isOverTop)
                    bottomSheetBehavior?.isDraggable = isOverTop
                }
            }
        )
    }

    private fun recalculatePeekBottomSheet() {
        val height = dbvApps.layoutParams.height
        val absoluteHeight = height + dbvApps.marginTop + dbvApps.marginBottom
        bottomSheetBehavior?.peekHeight = absoluteHeight
        if (bottomSheetBehavior?.isClosed == true) {
            oclPanel.setMarginTop(absoluteHeight)
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        view?.hideKeyboard()
        if (newState in listOf(STATE_HIDDEN, STATE_COLLAPSED)) {
            viewModel.collapseAll()
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        val anchorWithInaccuracy = bottomSheetBehavior?.halfExpandedRatio ?: return
        val anchor = anchorWithInaccuracy - BOTTOM_SHEET_HALF_RATIO_INACCURACY
        appsBoardSlideHandler.slideToTop(slideOffset, anchor)
    }
}
