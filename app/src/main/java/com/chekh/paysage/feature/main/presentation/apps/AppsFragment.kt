package com.chekh.paysage.feature.main.presentation.apps

import android.os.Bundle
import android.view.View
import androidx.core.view.*
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.handler.backpressed.BackPressedHandler
import com.chekh.paysage.core.handler.backpressed.SlidingPanelBackPressedHandler
import com.chekh.paysage.core.handler.slide.AppsBoardSlideHandler
import com.chekh.paysage.core.tools.lazyUnsafe
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior
import com.chekh.paysage.core.ui.behavior.CustomBottomSheetBehavior.*
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.tools.hideKeyboard
import com.chekh.paysage.feature.main.presentation.apps.adapter.AppGroupAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_apps.*

@AndroidEntryPoint
class AppsFragment : BaseFragment(R.layout.fragment_apps), BottomSheetCallback {

    private val appsViewModel: AppsViewModel by viewModels()
    private val appDockViewModel: AppDockViewModel by activityViewModels()

    private val adapter: AppGroupAdapter by lazyUnsafe {
        AppGroupAdapter(appsViewModel::toggleCategory, appsViewModel::onGroupScrollOffsetChanged)
    }

    private var bottomSheetBehavior: CustomBottomSheetBehavior<View>? = null

    private val backPressedHandler: BackPressedHandler by lazyUnsafe {
        SlidingPanelBackPressedHandler(bottomSheetBehavior, srvCategories, childFragmentManager)
    }

    private val appsBoardSlideHandler by lazyUnsafe {
        AppsBoardSlideHandler(dbvApps, oclPanel)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyWindowInsets()
        setupListView()
        setupParentSlidingPanel(view)
        setupViewModel()
    }

    private fun setupViewModel() {
        appsViewModel.init(Unit)
        appDockViewModel.init(Unit)

        appsViewModel.scrollPosition.observe(viewLifecycleOwner) { position ->
            srvCategories.smoothScrollToHeader(position)
        }
        appsViewModel.boardAppSettings.observe(viewLifecycleOwner) { appSettings ->
            adapter.setAppSettings(appSettings)
        }
        appsViewModel.appGroups.observe(viewLifecycleOwner) { categories ->
            adapter.setAppGroups(categories)
        }
        appDockViewModel.dockAppSettings.observe(viewLifecycleOwner) { appSettings ->
            dbvApps.setAppSettings(appSettings)
            if (dbvApps.layoutHeight != appSettings.appSize) {
                dbvApps.layoutHeight = appSettings.appSize
                appDockViewModel.appDockSize.value = dbvApps.layoutHeight
                recalculatePeekBottomSheet()
            }
        }
        appDockViewModel.dockApps.observe(viewLifecycleOwner) { dockApps ->
            dbvApps.setApps(dockApps)
        }
    }

    private fun applyWindowInsets() {
        val smallDimen = resources.getDimension(R.dimen.small).toInt()
        applyWindowInsets { insets ->
            srvCategories.updatePadding(bottom = smallDimen + insets.navigationBar)
            appsBoardSlideHandler.setExpandedMarginTop(insets.statusBar)
            if (bottomSheetBehavior?.isOpened == false) {
                dbvApps.bottomMargin = smallDimen + insets.navigationBar
                recalculatePeekBottomSheet()
            } else if (bottomSheetBehavior?.state == STATE_EXPANDED) {
                oclPanel.topMargin = insets.statusBar
            }
        }
    }

    private fun setupParentSlidingPanel(view: View) {
        val parent = view.parent?.parent as? View ?: return
        bottomSheetBehavior = from(parent)
        bottomSheetBehavior?.addBottomSheetCallback(this)
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
            oclPanel.topMargin = absoluteHeight
        }
    }

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        view?.hideKeyboard()
        if (newState in listOf(STATE_HIDDEN, STATE_COLLAPSED)) {
            appsViewModel.collapseAll()
        }
    }

    override fun onSlide(bottomSheet: View, slideOffset: Float) {
        val anchor = bottomSheetBehavior?.halfExpandedRatio ?: return
        appsBoardSlideHandler.slideToTop(slideOffset, anchor)
    }

    override fun onBackPressed(): Boolean {
        return backPressedHandler.onBackPressed()
    }
}
