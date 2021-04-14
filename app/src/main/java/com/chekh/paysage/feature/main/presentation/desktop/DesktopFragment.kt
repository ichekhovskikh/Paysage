package com.chekh.paysage.feature.main.presentation.desktop

import android.graphics.Rect
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.view.WindowInsets
import androidx.core.graphics.toRectF
import androidx.core.view.updatePadding
import androidx.fragment.app.activityViewModels
import com.chekh.paysage.R
import com.chekh.paysage.core.extension.*
import com.chekh.paysage.core.ui.fragment.BaseFragment
import com.chekh.paysage.core.ui.view.flow.FlowListAdapter
import com.chekh.paysage.feature.main.presentation.DesktopActivity
import com.chekh.paysage.feature.main.presentation.DesktopInsetsViewModel
import com.chekh.paysage.feature.main.presentation.desktop.adapter.DesktopWidgetFlowListItem
import com.chekh.paysage.feature.main.presentation.desktop.tools.DesktopGridProvider
import com.chekh.paysage.feature.main.presentation.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_apps.*
import kotlinx.android.synthetic.main.fragment_desktop.*
import javax.inject.Inject

@AndroidEntryPoint
class DesktopFragment : BaseFragment(R.layout.fragment_desktop), DesktopGridProvider {

    private val desktopViewModel: DesktopViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val insetsViewModel: DesktopInsetsViewModel by activityViewModels()

    @Inject
    lateinit var params: Params

    private val adapter by lazy { FlowListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListView()
        setupListeners()
        setupViewModel()
    }

    private fun setupListView() {
        flWidgets.adapter = adapter
    }

    private fun setupListeners() {
        flWidgets.setOnGestureScaleAndLongPress {
            homeViewModel.isEnabledOverlayHomeButtonsLiveData.postValue(true)
        }
        adapter.setOnItemsCommittedListener { items ->
            val draggingItem = items.find { it is DesktopWidgetFlowListItem && it.isDragging }
            draggingItem ?: return@setOnItemsCommittedListener
            val bounds = flWidgets.getItemBounds(draggingItem)
            bounds.offset(flWidgets.startMargin, flWidgets.topMargin)
            (activity as? DesktopActivity)?.setTargetDragViewBounds(bounds.toRectF())
        }
    }

    private fun setupViewModel() {
        insetsViewModel.windowInsetsLiveData.observe(viewLifecycleOwner, ::onApplyWindowInsets)
        desktopViewModel.desktopGridSizeLiveData.observe(viewLifecycleOwner) { (columns, rows) ->
            flWidgets.setSize(columns.toInt(), rows.toInt())
        }
        desktopViewModel.getDesktopWidgetsLiveData(params.pageId)
            .observe(viewLifecycleOwner) { items ->
                adapter.items = items
            }
        desktopViewModel.dockAppSizeLiveData.observe(viewLifecycleOwner) { appSize ->
            flWidgets.updatePadding(bottom = appSize)
        }
    }

    @Suppress("DEPRECATION")
    private fun onApplyWindowInsets(insets: WindowInsets) {
        val decoratedMargin = resources.getDimension(R.dimen.small).toInt()
        flWidgets.bottomMargin = insets.systemWindowInsetBottom + decoratedMargin
        flWidgets.topMargin = insets.systemWindowInsetTop + decoratedMargin
    }

    override fun getOccupiedCells(bounds: RectF): Rect {
        flWidgets ?: return Rect()
        val offsetBounds = bounds.copyOffset(
            horizontalOffset = -flWidgets.startMargin.toFloat(),
            verticalOffset = -flWidgets.topMargin.toFloat()
        )
        return flWidgets.getOccupiedCells(offsetBounds)
    }

    override fun getGridBounds() = flWidgets?.bounds ?: Rect()

    @Parcelize
    data class Params(val pageId: Long) : Parcelable
}
